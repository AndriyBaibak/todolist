package ua.baibak.todolist.service1;

public class Validate {
    public static void validateTaskData(String description, String deadline) throws Exception {
        if (description.equals("") || deadline.equals("")) {
            throw new Exception("Неправельно введені данні. Спробуйте ще раз!");
        }
    }
}
