package io.github.chrisruffalo.htmx.templates;

import io.github.chrisruffalo.htmx.model.UserTask;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import java.util.List;

/**
 * Fragments are used in the entrypoint templates themselves as well
 * as rendered on the server side when interactive things happen like
 * updating a single part of the page.
 */
@CheckedTemplate(basePath = "fragments")
public class Fragments {

    public static native TemplateInstance clicks(long clicks);

    public static native TemplateInstance tasks(List<UserTask> tasks);

    public static native TemplateInstance task(UserTask task);

    public static native TemplateInstance task_form(UserTask task);

}
