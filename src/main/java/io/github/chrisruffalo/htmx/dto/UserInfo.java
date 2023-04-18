package io.github.chrisruffalo.htmx.dto;

import io.github.chrisruffalo.htmx.model.UserTask;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the information that goes back to the templates or back
 * to the user on request.
 */
public class UserInfo {
    private String id;

    private String username;

    private long clicks;

    private List<UserTask> tasks = new ArrayList<>(0);

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public List<UserTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<UserTask> tasks) {
        this.tasks = tasks;
    }
}
