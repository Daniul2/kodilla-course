package com.kodilla.hibernate.task.tasklist;

import com.kodilla.hibernate.tasklist.TaskList;
import com.kodilla.hibernate.tasklist.TaskListDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class TaskListDaoTestSuite {
    @Autowired
    private TaskListDao taskListDao;

    @Test
    void testFindByListName(){
        TaskList taskList = new TaskList("Test List", "Some description");
        taskListDao.save(taskList);

        List<TaskList> result = taskListDao.findByListName("Test List");
        assertFalse(result.isEmpty());

        Long id = taskList.getId();
        taskListDao.deleteById(id);
    }
}
