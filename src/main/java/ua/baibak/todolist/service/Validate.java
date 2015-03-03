package ua.baibak.todolist.service;

public class Validate {
    public Validate(){

    }
    public void validateTaskData(String description, String deadline) throws Exception {
        if (description.equals("") || deadline.equals("")) {
            throw new Exception("Неправельно введені данні. Спробуйте ще раз!");
        }
    }
}
