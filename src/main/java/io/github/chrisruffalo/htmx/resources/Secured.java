package io.github.chrisruffalo.htmx.resources;


import io.github.chrisruffalo.htmx.dto.UserInfo;
import io.github.chrisruffalo.htmx.model.UserClicks;
import io.github.chrisruffalo.htmx.templates.Fragments;
import io.github.chrisruffalo.htmx.templates.Templates;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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

}