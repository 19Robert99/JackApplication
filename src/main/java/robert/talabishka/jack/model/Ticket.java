package robert.talabishka.jack.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tickets", schema = "public", catalog = "jack")
public class Ticket extends BaseEntity {
    private String acceptanceCriteria;
    private Long epicId;
    private String description;
    private Date dueDate;
    private Date createdDate;
    private Date resolvedDate;
    private Float estimatedTime;
    private Float loggedTime;
    private String name;
    private String key;
    private String status;
    private String type;
    private String priority;
    private Boolean isSubTask;
    private Long parentTicketId;
    private Collection<Comment> comments;
    private Project project;
    private Collection<Attachment> attachments;
    private Collection<Commit> commits;
    private User assignee;
    private User reporter;
    private Set<User> watchers;
    private Set<Build> builds;
    private Set<Label> labels;
    private Set<Component> components;
    private Collection<SubTask> subTasks;

    @Column(name = "acceptance_criteria")
    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    @Column(name = "epic_id")
    public Long getEpicId() {
        return epicId;
    }

    public void setEpicId(Long epicId) {
        this.epicId = epicId;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "due_date")
    @JsonFormat(pattern="dd/MMM/yy", timezone= "Europe/Kiev")
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Column(name = "created_date")
    @JsonFormat(pattern="dd/MMM/yy hh:mm a", timezone= "Europe/Kiev")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "resolved_date")
    @JsonFormat(pattern="dd/MMM/yy hh:mm a", timezone= "Europe/Kiev")
    public Date getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Date resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    @Column(name = "estimated_time")
    public Float getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Float estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    @Column(name = "logged_time")
    public Float getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(Float loggedTime) {
        this.loggedTime = loggedTime;
    }

    @Column(name = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "priority")
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Column(name = "is_sub_task")
    public Boolean getSubTask() {
        return isSubTask;
    }

    public void setSubTask(Boolean subTask) {
        this.isSubTask = subTask;
    }

    @Column(name = "parent_ticket_id")
    public Long getParentTicketId() {
        return parentTicketId;
    }

    public void setParentTicketId(Long parentTicketId) {
        this.parentTicketId = parentTicketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(acceptanceCriteria, ticket.acceptanceCriteria) &&
                Objects.equals(epicId, ticket.epicId) &&
                Objects.equals(description, ticket.description) &&
                Objects.equals(dueDate, ticket.dueDate) &&
                Objects.equals(createdDate, ticket.createdDate) &&
                Objects.equals(resolvedDate, ticket.resolvedDate) &&
                Objects.equals(estimatedTime, ticket.estimatedTime) &&
                Objects.equals(loggedTime, ticket.loggedTime) &&
                Objects.equals(name, ticket.name) &&
                Objects.equals(status, ticket.status) &&
                Objects.equals(type, ticket.type) &&
                Objects.equals(priority, ticket.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acceptanceCriteria, epicId, description, dueDate, createdDate, resolvedDate, estimatedTime, loggedTime, name, status, type, priority);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticket")
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    @JsonIncludeProperties({"id"})
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticket")
    public Collection<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<Attachment> attachments) {
        this.attachments = attachments;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticket")
    public Collection<Commit> getCommits() {
        return commits;
    }

    public void setCommits(Collection<Commit> commits) {
        this.commits = commits;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "assignee_id", referencedColumnName = "id", nullable = false)
    @JsonIncludeProperties({"id", "fullName"})
    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reporter_id", referencedColumnName = "id", nullable = false)
    @JsonIncludeProperties({"id", "fullName"})
    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    @ManyToMany
    @JoinTable(name = "watchers",
            joinColumns = @JoinColumn(name = "ticket_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @JsonIncludeProperties({"id", "fullName"})
    public Set<User> getWatchers() {
        return watchers;
    }

    public void setWatchers(Set<User> watchers) {
        this.watchers = watchers;
    }

    @ManyToMany
    @JoinTable(name = "ticket_builds",
            joinColumns = @JoinColumn(name = "ticket_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "build_id", referencedColumnName = "id")
    )
    public Set<Build> getBuilds() {
        return builds;
    }

    public void setBuilds(Set<Build> builds) {
        this.builds = builds;
    }

    @ManyToMany
    @JoinTable(name = "ticket_labels",
            joinColumns = @JoinColumn(name = "ticket_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "label_id", referencedColumnName = "id")
    )
    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    @ManyToMany
    @JoinTable(name = "ticket_components",
            joinColumns = @JoinColumn(name = "ticket_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "component_id", referencedColumnName = "id")
    )
    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rootTicket")
    @JsonIncludeProperties({"subTaskId", "type"})
    public Collection<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(Collection<SubTask> subTasks) {
        this.subTasks = subTasks;
    }
}
