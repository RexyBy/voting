package by.rexy.voting.web.user;

import by.rexy.voting.AuthUser;
import by.rexy.voting.model.Role;
import by.rexy.voting.model.User;
import by.rexy.voting.repository.UserRepository;
import by.rexy.voting.util.SecurityUtil;
import by.rexy.voting.util.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController extends AbstractUserRestController {
    static final String REST_URL = "/rest";

    public ProfileRestController(UserRepository repository) {
        super(repository);
    }

    @GetMapping("/profile")
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return super.get(authUser.id());
    }

    @PutMapping(value = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user) {
        AuthUser authUser = SecurityUtil.safeGet();
        ValidationUtil.assureIdConsistent(user, authUser.id());
        user.setRoles(authUser.getUser().getRoles());
        user.setLastTimeVoted(authUser.getUser().getLastTimeVoted());
        user.setVotedRestaurant(authUser.getUser().getVotedRestaurant());
        if (user.getId() == SecurityUtil.getAuthUserId()) {
            super.update(user, authUser.id());
        }
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody @Valid User user) {
        user.setRoles(Set.of(Role.USER));
        User createdUser = super.create(user);
        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(REST_URL + "/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewUser).body(createdUser);
    }

    @DeleteMapping("/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(SecurityUtil.getAuthUserId());
    }
}
