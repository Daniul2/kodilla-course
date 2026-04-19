package com.kodilla.hibernate.task.tasklist;

import com.kodilla.hibernate.task.Task;
import com.kodilla.hibernate.task.TaskFinancialDetails;
import com.kodilla.hibernate.tasklist.TaskList;
import com.kodilla.hibernate.tasklist.TaskListDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class TaskListDaoTestSuite {

    @Autowired
    private TaskListDao taskListDao;

    private static final String LISTNAME = "ToDo list";

    @Test
    void testFindByListName() {
        //Given
        TaskList taskList = new TaskList("Test List", "Some description");
        taskListDao.save(taskList);

        //When
        List<TaskList> result = taskListDao.findByListName("Test List");

        //Then
        assertFalse(result.isEmpty());

        //CleanUp
        taskListDao.deleteById(taskList.getId());
    }

    @Test
    void testTaskListDaoSaveWithTasks() {
        //Given
        Task task = new Task("Test: Learn Hibernate", 14);
        Task task2 = new Task("Test: Write some entities", 3);

        TaskFinancialDetails tfd = new TaskFinancialDetails(new BigDecimal(20), false);
        TaskFinancialDetails tfd2 = new TaskFinancialDetails(new BigDecimal(10), false);

        task.setTaskFinancialDetails(tfd);
        task2.setTaskFinancialDetails(tfd2);

        TaskList taskList = new TaskList(LISTNAME, "ToDo tasks");

        taskList.getTasks().add(task);
        taskList.getTasks().add(task2);

        task.setTaskList(taskList);
        task2.setTaskList(taskList);

        //When
        taskListDao.save(taskList);
        int id = taskList.getId();

        //Then
        assertNotEquals(0, id);

        //CleanUp
       // taskListDao.deleteById(id);
    }
}
