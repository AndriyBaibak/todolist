package ua.baibak.todolist.service.task;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.baibak.todolist.dao.TaskDao;
import ua.baibak.todolist.entity.Task;

import java.util.List;
public class TaskService implements ActionWithTask {

    private TaskDao taskDao;

    public TaskService(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void createAndSaveNewTask(Task taskForSave, String author) throws Exception {
        taskDao.save(taskForSave,author);
    }

    @Override
    public void deleteTask(String idForDelete,String author) throws Exception {
        int idTask = Integer.parseInt(idForDelete);
        taskDao.deleteTask(idTask,author);
    }

    @Override
    public List getAllTasks(String author) throws Exception {
        return taskDao.getAllTasks(author);
    }

    @Override
    public void updateTasks(Task taskForUpdate, String id,String author) throws Exception {
        taskDao.updateTask(taskForUpdate, id,author);
    }

}