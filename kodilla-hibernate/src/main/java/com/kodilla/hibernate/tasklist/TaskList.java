package com.kodilla.hibernate.tasklist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TASKLISTS")

public class TaskList {
    @Id
    @GeneratedValue
    private Long id;
    private String listName;
    private String description;

    public TaskList() {
    }

    public TaskList(String listName, String description) {
        this.listName = listName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }
}
