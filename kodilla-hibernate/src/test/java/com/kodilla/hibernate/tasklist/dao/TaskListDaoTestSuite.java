package com.kodilla.hibernate.tasklist.dao;

import com.kodilla.hibernate.tasklist.TaskList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TaskListDaoTestSuite {

    @Autowired
    private TaskListDao taskListDao;

    private static final String LIST_NAME = "To Do List";
    private static final String DESCRIPTION = "Testing the task list entity";

    @Test
    void testFindByListName() {
        //Given
        TaskList taskList = new TaskList(LIST_NAME, DESCRIPTION);
        taskListDao.save(taskList);

        //When
        List<TaskList> readTaskLists = taskListDao.findByListName(LIST_NAME);

        //Then
        assertEquals(1, readTaskLists.size());
        assertEquals(LIST_NAME, readTaskLists.get(0).getListName());

        int id = readTaskLists.get(0).getId();
        taskListDao.deleteById(id);
    }
}
