package com.mori5.itsecurity.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "request_log")
@EntityListeners(AuditingEntityListener.class)
public class RequestLog {
    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "uuid_generator")
    @GenericGenerator(name = "uuid_generator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    private User actor;

    private String method;
    private String path;

    private int responseCode;

    @CreatedDate
    private Instant loggedAt;
}
