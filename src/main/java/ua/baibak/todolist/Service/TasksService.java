package ua.baibak.todolist.service;

import org.apache.log4j.Logger;
import ua.baibak.todolist.dao.HibernateTasksDAO;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.interfaces.ActionWithTasks;
import ua.baibak.todolist.interfaces.TasksDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TasksService implements ActionWithTasks{

    public  TasksDAO hibernateTasksDAO = new HibernateTasksDAO();
    public  SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
    public  List tasks = new ArrayList<Tasks>();
    static Logger log = org.apache.log4j.Logger.getLogger(TasksService.class);
    public static TasksService objectToActionTasks = new TasksService();
    public static TasksService getObjectToActionTasks() {
        return objectToActionTasks;
    }

    @Override
    public  void createAndSaveNewTask(String description, String deadline) throws Exception {
        if(description == ""){
            throw new Exception("Опис завдання відсутній.");
        }
        log.debug("========================== before" + deadline.toString());

        System.out.print(deadline);
        java.sql.Date dateDeadline = java.sql.Date.valueOf(deadline);
        log.debug("==========================" + TasksService.changeSQLDateToUtil(dateDeadline));
        hibernateTasksDAO.save(description, TasksService.changeSQLDateToUtil(dateDeadline));
    }
    @Override
    public  void deleteTask(String idForDelete)throws Exception{
        int idTask = Integer.parseInt(idForDelete);
        hibernateTasksDAO.deleteTasks(idTask);
    }
    @Override
    public  List getAllTasks() throws Exception {
        log.debug("in taskservice");
        tasks = hibernateTasksDAO.getAllTasks();
        return tasks;
    }
    @Override
    public void updateTasks(String newData, String id, String type)throws Exception{
        hibernateTasksDAO.updateTasks(newData,id,type);
    }

    public static Date changeSQLDateToUtil(java.sql.Date sqldate) {
        java.util.Date utildate = new java.util.Date(sqldate.getTime());
        return utildate;
    }

    public static java.sql.Date changeUtilDateToSQL(java.util.Date utildate) {
        java.sql.Date sqldate=new java.sql.Date(utildate.getTime());
        return sqldate;
    }


}