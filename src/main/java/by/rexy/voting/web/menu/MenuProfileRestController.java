package by.rexy.voting.web.menu;

import by.rexy.voting.AuthUser;
import by.rexy.voting.model.Menu;
import by.rexy.voting.repository.MenuRepository;
import by.rexy.voting.repository.RestaurantRepository;
import by.rexy.voting.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuProfileRestController extends AbstractMenuController {
    static final String REST_URL = "/rest/profile/restaurant/menu";

    public MenuProfileRestController(MenuRepository menuRepository,
                                     RestaurantRepository restaurantRepository,
                                     UserRepository userRepository) {
        super(menuRepository, restaurantRepository, userRepository);
    }

    @GetMapping
    public List<Menu> getMenuForRestaurantOnDate(@RequestParam int restaurantId) {
        return super.getAllForRestaurantOnDate(restaurantId, getCurrentDate());
    }

    @GetMapping("/history")
    public List<Menu> getAllForRestaurant(@RequestParam int restaurantId) {
        return super.getAllForRestaurant(restaurantId);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        super.vote(id, authUser.getUser());
    }

    private LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}