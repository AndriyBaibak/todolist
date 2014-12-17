package entity;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "createdDate")
    private java.sql.Date createdDate;

    @Column(name = "deadline")
    private java.sql.Date deadline;

    public Tasks(String task, java.sql.Date deadline){
        this.description = task;
        this.createdDate =new java.sql.Date(new java.util.Date().getTime());
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

    public java.sql.Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(java.sql.Date createdDate) {
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
}
