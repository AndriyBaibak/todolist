package ua.baibak.todolist.entity;

import javax.persistence.*;

/**
 * Created by Андрей on 23.11.2014.
 */
@Entity
@Table(name = "tasks")
public class Tasks {
    @Id
    @Column(name = "idTasks")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int idTasks;

    @Column(name = "description")
    private String description;

    //@Temporal(TemporalType.DATE)
    @Column(name = "createdDate")
    private java.util.Date createdDate;

    @Column(name = "deadline")
    private java.sql.Date deadline;

    public Tasks(String task, java.sql.Date deadline){
        this.description = task;
        this.createdDate = new java.util.Date();
        this.deadline = deadline;
    }
    public Tasks() {
    }
    public String getDescription() {
        return description;
    }

    public int getIdTasks() {
        return idTasks;
    }

    public void setIdTasks(int idTasks) {
        this.idTasks = idTasks;
    }

    public java.util.Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public java.sql.Date getDeadline() {
        return deadline;
    }

    public void setDeadline(java.sql.Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(idTasks);
        sb.append(" ").append(description);
        sb.append(" ").append(createdDate);
        sb.append(" ").append(deadline);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tasks)) return false;

        Tasks tasks = (Tasks) o;

        if (createdDate != null ? !createdDate.equals(tasks.createdDate) : tasks.createdDate != null) return false;
        if (deadline != null ? !deadline.equals(tasks.deadline) : tasks.deadline != null) return false;
        if (description != null ? !description.equals(tasks.description) : tasks.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        return result;
    }
}
