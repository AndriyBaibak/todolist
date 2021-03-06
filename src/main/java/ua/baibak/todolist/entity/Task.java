package ua.baibak.todolist.entity;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class Task {

    public int id;

    @NotEmpty(message = "Please enter your description")
    @Size(min = 6, max = 200, message = "Description must be between 6 and 200 characters")
    private String description;

    private Date createdDate;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date deadline;

    private String author;

    public Task(int id, String description, Date createdDate, Date deadline, String author) {
        this.id = id;
        this.description = description;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.author = author;
    }

    public Task() {
        this.createdDate = new Date();
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(" ").append(description);
        sb.append(" ").append(createdDate);
        sb.append(" ").append(deadline);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (createdDate != null ? !createdDate.equals(task.createdDate) : task.createdDate != null) return false;
        if (deadline != null ? !deadline.equals(task.deadline) : task.deadline != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null) return false;

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
