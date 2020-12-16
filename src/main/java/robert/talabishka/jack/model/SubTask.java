package robert.talabishka.jack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_tasks", schema = "public", catalog = "jack")
public class SubTask extends BaseEntity{
    private String type;
    @JsonIgnore
    private Ticket rootTicket;
    @JsonIgnore
    private Ticket subTicket;
    private Long subTaskId;

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(type, subTask.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id", nullable = false)
    public Ticket getRootTicket() {
        return rootTicket;
    }

    public void setRootTicket(Ticket rootTicket) {
        this.rootTicket = rootTicket;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sub_task_id", referencedColumnName = "id", nullable = false)
    public Ticket getSubTicket() {
        return subTicket;
    }

    public void setSubTicket(Ticket subTicket) {
        this.subTicket = subTicket;
    }

    @Column(name = "sub_task_id", insertable = false, updatable = false)
    public Long getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(Long subTaskId) {
        this.subTaskId = subTaskId;
    }
}
