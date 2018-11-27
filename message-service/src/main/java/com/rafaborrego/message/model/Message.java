package com.rafaborrego.message.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Message")
public class Message {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    /**
     * In a real application we should encrypt the content
     *
     * Example using PostgreSQL:
     * https://www.thoughts-on-java.org/map-encrypted-database-columns-hibernates-columntransformer-annotation
     */
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "creation_timestamp", nullable = false)
    private LocalDateTime creationTimestamp;

    @Column(name = "last_update_timestamp", nullable = false)
    private LocalDateTime lastUpdateTimestamp;

    @Column(name = "deleted")
    private boolean deleted;
}
