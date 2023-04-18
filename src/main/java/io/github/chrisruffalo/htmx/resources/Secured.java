package io.github.chrisruffalo.htmx.resources;


import io.github.chrisruffalo.htmx.dto.UserInfo;
import io.github.chrisruffalo.htmx.model.UserClicks;
import io.github.chrisruffalo.htmx.model.UserTask;
import io.github.chrisruffalo.htmx.templates.Fragments;
import io.github.chrisruffalo.htmx.templates.Templates;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.panache.common.Parameters;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Collections;
import java.util.Optional;

/**
 * A simple entry point for secured (requiring login) pages and resources.
 */
@Authenticated
@Path("/secured")
public class Secured {

    @Inject
    SecurityIdentity identity;

    private UserInfo getUserInfo() {
        final UserInfo info = new UserInfo();
        info.setUsername(identity.getPrincipal().getName());
        if (identity.getPrincipal() instanceof final OidcJwtCallerPrincipal principal) {
            info.setId(principal.getSubject());
        }
        return info;
    }

    @GET
    @Path("/dashboard.html")
    @Produces(MediaType.TEXT_HTML)
    @Transactional
    @Blocking
    public TemplateInstance dashboard() {
        final UserInfo info = this.getUserInfo();
        final UserClicks clicks = UserClicks.forUser(info);
        info.setClicks(clicks.count);
        info.setTasks(UserTask.forUser(info));
        return Templates.dashboard(info);
    }

    @GET
    @Path("/clicks/increment")
    @Produces(MediaType.TEXT_HTML)
    @Transactional
    @Blocking
    public TemplateInstance incrementClicks() {
        final UserInfo info = this.getUserInfo();
        final UserClicks clicks = UserClicks.forUser(info);
        clicks.count++;
        clicks.persist();
        return Fragments.clicks(clicks.count);
    }

    @GET
    @Path("/tasks")
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance tasks() {
        return Fragments.tasks(UserTask.forUser(getUserInfo()));
    }

    @GET
    @Path("/tasks/add")
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance addNewTask() {
        return Fragments.task_form(new UserTask());
    }

    @GET
    @Path("/tasks/{taskId}/edit")
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    @Transactional
    public TemplateInstance editTask(@PathParam("taskId") String taskId) {
        final UserTask task = UserTask.findByUserAndTaskId(getUserInfo(), taskId).orElseGet(() -> {final UserTask t = new UserTask(); t.userId = getUserInfo().getId(); return t;});
        return Fragments.task_form(task);
    }

    @POST
    @Path("/tasks/create")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Blocking
    @Transactional
    public TemplateInstance createTask(@FormParam("taskDescription") final String text) {
        final UserInfo info = getUserInfo();
        final UserTask task = new UserTask();
        task.userId = info.getId();
        task.text = text;
        task.persist();
        return Fragments.tasks(UserTask.forUser(getUserInfo()));
    }

    @POST
    @Path("/tasks/{taskId}/update")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Blocking
    @Transactional
    public TemplateInstance updateTask(@PathParam("taskId") String taskId, @FormParam("taskDescription") final String text) {
        UserTask.findByUserAndTaskId(getUserInfo(), taskId).ifPresent(t -> {
            t.text = text;
            t.persist();
        });
        return Fragments.tasks(UserTask.forUser(getUserInfo()));
    }

    @DELETE
    @Path("/tasks/{taskId}/delete")
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    @Transactional
    public TemplateInstance deleteTask(@PathParam("taskId") final String taskId) {
        final UserInfo i = getUserInfo();
        UserTask.findByUserAndTaskId(getUserInfo(), taskId).ifPresent(t -> {
            t.deleted = true;
            t.persist();
        });
        return Fragments.tasks(UserTask.forUser(getUserInfo()));
    }

    @POST
    @Path("/tasks/{taskId}/completed")
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    @Transactional
    public TemplateInstance completeTask(@PathParam("taskId") final String taskId) {
        final Optional<UserTask> t = UserTask.findByUserAndTaskId(getUserInfo(), taskId);
        if (t.isPresent()) {
            t.ifPresent(u -> { u.completed = true; u.persist(); });
            return Fragments.task(t.get());
        }
        return null;
    }

    @POST
    @Path("/tasks/{taskId}/incompleted")
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    @Transactional
    public TemplateInstance incompleteTask(@PathParam("taskId") final String taskId) {
        final Optional<UserTask> t = UserTask.findByUserAndTaskId(getUserInfo(), taskId);
        if (t.isPresent()) {
            t.ifPresent(u -> { u.completed = false; u.persist(); });
            return Fragments.task(t.get());
        }
        return null;
    }

}