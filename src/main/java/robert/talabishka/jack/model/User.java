package robert.talabishka.jack.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "public", catalog = "jack")
public class User extends BaseEntity {
    private String name;
    private String fullName;
    private String email;
    private String password;
    private String avatar;
    private Date registrationDate;
    private Collection<Comment> comments;
    private Collection<Commit> commits;
    private Collection<Project> projects;
    private Set<Ticket> watchedTickets;
    private Collection<Ticket> assignedTickets;
    private Collection<Ticket> reportedTickets;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Column(name = "registration_date")
    @JsonFormat(pattern="dd/MMM/yy", timezone= "Europe/Kiev")
    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(avatar, user.avatar) &&
                Objects.equals(registrationDate, user.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fullName, email, password, avatar, registrationDate);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reporter")
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Collection<Commit> getCommits() {
        return commits;
    }

    public void setCommits(Collection<Commit> commits) {
        this.commits = commits;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @JsonIncludeProperties({"id"})
    public Collection<Project> getProjects() {
        return projects;
    }

    public void setProjects(Collection<Project> projects) {
        this.projects = projects;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "watchers")
    @JsonIncludeProperties({"id"})
    public Set<Ticket> getWatchedTickets() {
        return watchedTickets;
    }

    public void setWatchedTickets(Set<Ticket> watchedTickets) {
        this.watchedTickets = watchedTickets;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignee")
    @JsonIncludeProperties({"id"})
    public Collection<Ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(Collection<Ticket> assignedTickets) {
        this.assignedTickets = assignedTickets;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reporter")
    @JsonIncludeProperties({"id"})
    public Collection<Ticket> getReportedTickets() {
        return reportedTickets;
    }

    public void setReportedTickets(Collection<Ticket> reportedTickets) {
        this.reportedTickets = reportedTickets;
    }
}
