package io.github.chrisruffalo.htmx.model;

import io.github.chrisruffalo.htmx.dto.UserInfo;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import io.smallrye.common.annotation.Blocking;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Optional;

@Entity(name = "user_tasks")
public class UserTask extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    public String id;

    /**
     * This is lazy, I don't want a root user object
     */
    @Column
    public String userId;

    @Column
    public boolean completed = false;

    @Column
    public String text = "";

    @Column
    public boolean deleted = false;

    @Blocking
    public static List<UserTask> forUser(final UserInfo info) {
        return list("userId = :userId and deleted = false", Parameters.with("userId", info.getId()));
    }

    public static Optional<UserTask> findByUserAndTaskId(final UserInfo userInfo, final String taskId) {
        return find("userId = :userId and id = :taskId and deleted = false",
                Parameters.with("userId", userInfo.getId()).and("taskId", taskId)).firstResultOptional();
    }

}
