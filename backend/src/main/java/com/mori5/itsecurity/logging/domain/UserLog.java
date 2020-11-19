package com.mori5.itsecurity.logging.domain;

import com.mori5.itsecurity.domain.User;
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
@Table(name = "user_log")
@EntityListeners(AuditingEntityListener.class)
public class UserLog {
    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "uuid_generator")
    @GenericGenerator(name = "uuid_generator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    private User actor;
    private String operation;

    @ManyToOne
    private User profileOf;

    @CreatedDate
    private Instant loggedAt;
}