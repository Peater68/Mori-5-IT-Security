package com.mori5.itsecurity.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document")
@EqualsAndHashCode(exclude = {"id", "modifiedAt", "createdAt"})
@EntityListeners(AuditingEntityListener.class)
public class Document {
    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "uuid_generator")
    @GenericGenerator(name = "uuid_generator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String fileName;

    private String creator;
    private Instant createdDate;
    private String tags;
    private String caption;
    private Long duration;
    private Long caffContentSize;

    @ManyToOne(fetch = FetchType.LAZY)
    private User uploader;
    @ManyToMany(mappedBy = "downloads", fetch = FetchType.LAZY)
    private List<User> customers;
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant modifiedAt;
}
