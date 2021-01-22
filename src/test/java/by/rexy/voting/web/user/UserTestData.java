package by.rexy.voting.web.user;

import by.rexy.voting.model.Role;
import by.rexy.voting.model.User;
import by.rexy.voting.web.JsonUtil;
import by.rexy.voting.web.TestMatcher;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "votedRestaurant", "password");

    final static int USER_ID = 100003;
    final static int ADMIN_ID = 100004;

    static User user = new User(USER_ID,
            "User",
            "user@yandex.ru",
            "password",
            Set.of(Role.USER),
            LocalDateTime.of(2020, 12, 22, 15, 0));

    static User admin = new User(ADMIN_ID,
            "Admin",
            "admin@gmail.com",
            "admin",
            Set.of(Role.USER, Role.ADMIN),
            null);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", Collections.singleton(Role.USER), null);
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setRoles(Set.of(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}