package io.github.chrisruffalo.htmx.templates;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@CheckedTemplate(basePath = "fragments")
public class Fragments {

    public static native TemplateInstance clicks(long clicks);

}
