package ua.baibak.todolist.interfaces;

import java.util.List;

/**
 * Created by Андрей on 19.01.2015.
 */
public interface ActionWithTasks {

    public void createAndSaveNewTask(String deadline, String description)throws Exception;

    public void deleteTask(String idForDelete)throws Exception;

    public List getAllTasks()throws Exception;

    public void updateTasks(String newDescription, String id)throws Exception;


}