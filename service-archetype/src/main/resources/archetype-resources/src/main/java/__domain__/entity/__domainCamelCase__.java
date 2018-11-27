package ${groupId}.${domain}.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "${domainCamelCase}")
public class ${domainCamelCase} {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_timestamp", nullable = false)
    private LocalDateTime creationTimestamp;

    @Column(name = "last_update_timestamp", nullable = false)
    private LocalDateTime lastUpdateTimestamp;

    @Column(name = "deleted")
    private boolean deleted;

    // Sample field so we can do some validations on the tests
    @Column(name = "content", nullable = false)
    private String content;
}
