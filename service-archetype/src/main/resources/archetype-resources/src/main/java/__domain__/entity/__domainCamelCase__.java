package ${groupId}.${domain}.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LocalDateTime getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
