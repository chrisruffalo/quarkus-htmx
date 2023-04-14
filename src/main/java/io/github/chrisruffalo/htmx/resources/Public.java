package io.github.chrisruffalo.htmx.resources;

import io.github.chrisruffalo.htmx.templates.Templates;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class Public {

    @GET
    @Path("/{a:|index.html}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        return Templates.index();
    }

}
