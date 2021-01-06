package by.rexy.voting.web.user;

import by.rexy.voting.AuthUser;
import by.rexy.voting.model.User;
import by.rexy.voting.repository.DataJpaUserRepository;
import by.rexy.voting.util.SecurityUtil;
import by.rexy.voting.util.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController extends AbstractUserRestController {
    static final String REST_URL = "/rest/profile/users";

    public ProfileRestController(DataJpaUserRepository repository) {
        super(repository);
    }

    @GetMapping
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return repository.get(authUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        AuthUser authUser = SecurityUtil.safeGet();
        ValidationUtil.assureIdConsistent(user, authUser.id());
        user.setRoles(authUser.getUser().getRoles());
        user.setLastTimeVoted(authUser.getUser().getLastTimeVoted());
        user.setVotedRestaurant(authUser.getUser().getVotedRestaurant());
        if (user.getId() == SecurityUtil.getAuthUserId()) {
            super.update(user, authUser.id());
        }
    }
}
