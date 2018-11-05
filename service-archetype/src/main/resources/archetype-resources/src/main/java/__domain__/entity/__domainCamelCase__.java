package ${groupId}.${domain}.entity;

import javax.persistence.*;

@Entity
@Table(name = "${domainCamelCase}")
public class ${domainCamelCase} {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}