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
    private Date createdDate;

    @Column(name = "deadline")
    private Date deadline;



    public Tasks(String task, Date deadline){
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(idTasks);
        sb.append(" ").append(description);
        sb.append(" ").append(createdDate);
        return sb.toString();
    }
}
