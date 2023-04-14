package io.github.chrisruffalo.htmx.model;

import io.github.chrisruffalo.htmx.dto.UserInfo;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.annotation.Blocking;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Optional;

/**
 * Save the clicks that are made by users
 */
@Entity(name = "user_clicks")
public class UserClicks extends PanacheEntityBase {

    @Id
    public String id;

    @Column
    public Long count;

    @Blocking
    public static UserClicks forUser(final UserInfo info) {
        return (UserClicks) UserClicks.findByIdOptional(info.getId()).orElseGet(() -> {
            final UserClicks clicks = new UserClicks();
            clicks.id = info.getId();
            clicks.count = 0L;
            clicks.persist();
            return clicks;
        });
    }
}
