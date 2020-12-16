package robert.talabishka.jack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Collection;
import java.util.Objects;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects", schema = "public", catalog = "jack")
public class Project extends BaseEntity {
    private String name;
    private String key;
    private Date creationDate;
    private String icon;
    private Collection<Component> components;
    private Collection<Label> labels;
    private Collection<Role> roles;
    private User owner;
    private Collection<Ticket> tickets;
    private Collection<AssignedUser> assignedUsers;
    private String currentUserProjectRole;


    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "creation_date")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(name, project.name) &&
                Objects.equals(creationDate, project.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, creationDate);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Collection<Component> getComponents() {
        return components;
    }

    public void setComponents(Collection<Component> components) {
        this.components = components;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Collection<Label> getLabels() {
        return labels;
    }

    public void setLabels(Collection<Label> labels) {
        this.labels = labels;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @JsonIncludeProperties({"id", "fullName", "name"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @JsonIncludeProperties({"id"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Collection<AssignedUser> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Collection<AssignedUser> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    @Transient
    public String getCurrentUserProjectRole() {
        return currentUserProjectRole;
    }

    public void setCurrentUserProjectRole(String currentUserProjectRole) {
        this.currentUserProjectRole = currentUserProjectRole;
    }
}
