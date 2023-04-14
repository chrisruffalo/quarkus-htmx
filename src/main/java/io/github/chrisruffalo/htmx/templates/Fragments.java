package io.github.chrisruffalo.htmx.templates;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

/**
 * Fragments are used in the entrypoint templates themselves as well
 * as rendered on the server side when interactive things happen like
 * updating a single part of the page.
 */
@CheckedTemplate(basePath = "fragments")
public class Fragments {

    public static native TemplateInstance clicks(long clicks);

}
