package by.rexy.voting.web.user;

import by.rexy.voting.AuthUser;
import by.rexy.voting.model.User;
import by.rexy.voting.repository.DataJpaUserRepository;
import by.rexy.voting.to.UserTo;
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

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController extends AbstractUserRestController {
    static final String REST_URL = "/rest/profile";

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
    public void update(@RequestBody @Valid UserTo userTo) {
        User user = new User(userTo);
        AuthUser authUser = SecurityUtil.safeGet();
        ValidationUtil.assureIdConsistent(user, authUser.id());
        user.setRoles(authUser.getUser().getRoles());
        user.setLastTimeVoted(authUser.getUser().getLastTimeVoted());
        user.setVotedRestaurant(authUser.getUser().getVotedRestaurant());
        if (user.getId() == SecurityUtil.getAuthUserId()) {
            super.update(user, authUser.id());
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody @Valid UserTo userTo){
        userTo.setRoles(null);
        User createdUser = super.create(new User(userTo));
        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(REST_URL + "/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewUser).body(createdUser);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(){
        super.delete(SecurityUtil.getAuthUserId());
    }
}
