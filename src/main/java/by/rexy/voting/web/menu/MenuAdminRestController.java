package by.rexy.voting.web.menu;

import by.rexy.voting.model.Menu;
import by.rexy.voting.repository.DataJpaMenuRepository;
import by.rexy.voting.repository.DataJpaRestaurantRepository;
import by.rexy.voting.repository.DataJpaUserRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuAdminRestController extends AbstractMenuController {
    static final String REST_URL = "/rest/admin/restaurant/menu";

    public MenuAdminRestController(DataJpaMenuRepository menuRepository,
                                   DataJpaRestaurantRepository restaurantRepository,
                                   DataJpaUserRepository userRepository) {
        super(menuRepository, restaurantRepository, userRepository);
    }

    @GetMapping
    public List<Menu> getAll(@RequestParam(required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            return super.getAll();
        } else {
            return super.getAllOnDate(date);
        }
    }

    @Override
    @GetMapping("/{id}")
    public Menu get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createAndGetLocation(@RequestBody @Valid Menu menu,
                                                     @RequestParam int restaurantId) {
        Menu createdMenu = super.create(menu, restaurantId);
        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(REST_URL + "/{id}")
                .buildAndExpand(createdMenu.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewUser).body(createdMenu);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Menu menu, @PathVariable int id) {
        super.update(menu, id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setVotes(@PathVariable int id, @RequestParam int votes) {
        super.setVotes(id, votes);
    }
}