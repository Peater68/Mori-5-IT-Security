package com.mori5.itsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "uuid_generator")
    @GenericGenerator(name = "uuid_generator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String commentMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Document document;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}
