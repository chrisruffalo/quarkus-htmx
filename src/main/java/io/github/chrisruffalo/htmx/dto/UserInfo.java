package io.github.chrisruffalo.htmx.dto;

/**
 * This is the information that goes back to the templates or back
 * to the user on request.
 */
public class UserInfo {
    private String id;

    private String username;

    private long clicks;

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
}
