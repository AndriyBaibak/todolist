package ua.baibak.todolist.service;

import java.util.Date;

public class Validate {
    public Validate(){}

    public void validateTaskData(String description, Date deadline) throws Exception {
        if (description.equals("") || deadline.equals("")) {
            throw new Exception("Неправельно введені данні. Спробуйте ще раз!");
        }
    }
}
