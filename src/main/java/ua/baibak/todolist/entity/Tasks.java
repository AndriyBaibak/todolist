package ua.baibak.todolist.entity;

import java.util.Date;

public class Tasks {

    public int id;

    private String description;

    private java.util.Date createdDate;

    private java.util.Date deadline;

    public Tasks(int id, String description, Date createdDate, java.util.Date deadline) {
        this.id = id;
        this.description = description;
        this.createdDate = createdDate;
        this.deadline = deadline;
    }
    public Tasks(String task, java.util.Date deadline) {
        this.description = task;
        this.createdDate = new java.util.Date();
        this.deadline = deadline;
    }

    public Tasks() {
        this.createdDate = new java.util.Date();
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

    public java.util.Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public java.util.Date getDeadline() {
        return deadline;
    }

    public void setDeadline(java.util.Date deadline) {
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