package by.rexy.voting.web.user;

import by.rexy.voting.model.Role;
import by.rexy.voting.model.User;
import by.rexy.voting.repository.UserRepository;
import by.rexy.voting.util.exceptions.ErrorType;
import by.rexy.voting.web.AbstractRestControllerTest;
import by.rexy.voting.web.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static by.rexy.voting.web.TestUtil.userHttpBasic;
import static by.rexy.voting.web.user.AdminRestController.REST_URL;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AdminRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private UserRepository repository;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.user, UserTestData.admin));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + UserTestData.user.getId())
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.user));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + 1)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(errorType(ErrorType.DATA_NOT_FOUND));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void createAndGetLocation() throws Exception {
        User newUser = UserTestData.getNew();

        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.admin))
                .content(UserTestData.jsonWithPassword(newUser, "newPass")))
                .andExpect(status().isCreated());

        User created = TestUtil.readFromJson(actions, User.class);
        int newId = created.id();
        newUser.setId(newId);
        UserTestData.USER_MATCHER.assertMatch(created, newUser);
        UserTestData.USER_MATCHER.assertMatch(repository.get(newId), newUser);
    }

    @Test
    void update() throws Exception {
        User updatedUser = UserTestData.getUpdated();

        updatedUser.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + UserTestData.USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.admin))
                .content(UserTestData.jsonWithPassword(updatedUser, "newPass")))
                .andDo(print())
                .andExpect(status().isNoContent());

        UserTestData.USER_MATCHER.assertMatch(repository.get(UserTestData.USER_ID), UserTestData.getUpdated());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + UserTestData.USER_ID)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(status().isNoContent());
        assertNull(repository.get(UserTestData.USER_ID));
    }

    @Test
    void createInvalid() throws Exception {
        User invalid = new User(null, null, "", "newPass", Set.of(Role.USER, Role.ADMIN), null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.admin))
                .content(UserTestData.jsonWithPassword(invalid, "newPass")))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        User invalid = new User(UserTestData.user);
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + UserTestData.USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.admin))
                .content(UserTestData.jsonWithPassword(invalid, "password")))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        User updated = new User(UserTestData.user);
        updated.setEmail("admin@gmail.com");
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + UserTestData.USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.admin))
                .content(UserTestData.jsonWithPassword(updated, "password")))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        User expected = new User(null, "New", "user@yandex.ru", "newPass", Set.of(Role.USER, Role.ADMIN), null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.admin))
                .content(UserTestData.jsonWithPassword(expected, "newPass")))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }
}