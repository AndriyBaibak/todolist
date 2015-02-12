package ua.baibak.todolist.service;

public class Validate {
    public static void validateTaskData(String description, String deadline)throws Exception{
        if (description.equals("")|| deadline.equals(""))
            throw new Exception("Введенні дані не є вірними. Спробуйте ще раз!");
    }
}
