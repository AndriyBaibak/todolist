package ua.baibak.todolist.service;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.baibak.todolist.dao.JdbcTasksDao;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.interfaces.ActionWithTasks;
import ua.baibak.todolist.interfaces.TasksDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TasksService implements ActionWithTasks {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
    private static Logger log = Logger.getLogger(TasksService.class);
    public TasksDao tasksDao;
    public SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
    public List tasks = new ArrayList<Tasks>();

    public static ApplicationContext getContext() {
        return context;
    }

    public void setTasksDao(TasksDao tasksDao) {

        this.tasksDao = tasksDao;
    }

    public TasksService(){

    }
    @Override
    public void createAndSaveNewTask(String description, String deadline) throws Exception {
        java.util.Date dateDeadline = sp.parse(deadline);
        tasksDao.save(description, dateDeadline);
    }

    @Override
    public void deleteTask(String idForDelete) throws Exception {
        int idTask = Integer.parseInt(idForDelete);
        tasksDao.deleteTasks(idTask);
    }

    @Override
    public List getAllTasks() throws Exception {
     //   log.debug(tasksDao.getClass().toString());
        tasks = tasksDao.getAllTasks();
        return tasks;
    }

    @Override
    public void updateTasks(String newData, String id, String type) throws Exception {
        tasksDao.updateTasks(newData, id, type);
    }
}