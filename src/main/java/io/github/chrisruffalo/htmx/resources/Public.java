package io.github.chrisruffalo.htmx.resources;

import io.github.chrisruffalo.htmx.templates.Templates;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Entrypoint for public/non-authenticated resources
 */
@Path("/")
public class Public {

    /**
     * The entrypoint / home page / index of the application
     *
     * @return a template instance
     */
    @GET
    @Path("/{a:|index.html}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        return Templates.index();
    }

}
