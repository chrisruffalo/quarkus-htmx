package io.github.chrisruffalo.htmx.templates;

import io.github.chrisruffalo.htmx.dto.UserInfo;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

/**
 * Templates are entire pages that are served by resources and serve
 * as entry points into the application
 */
@CheckedTemplate
public class Templates {

    public static native TemplateInstance index();

    public static native TemplateInstance dashboard(UserInfo info);

}
