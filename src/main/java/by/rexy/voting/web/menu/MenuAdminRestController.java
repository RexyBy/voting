package by.rexy.voting.web.menu;

import by.rexy.voting.repository.DataJpaMenuRepository;
import by.rexy.voting.repository.DataJpaRestaurantRepository;
import by.rexy.voting.repository.DataJpaUserRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = MenuAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuAdminRestController extends AbstractMenuController {
    static final String REST_URL = "/rest/admin/menu";

    public MenuAdminRestController(DataJpaMenuRepository menuRepository,
                                   DataJpaRestaurantRepository restaurantRepository,
                                   DataJpaUserRepository userRepository) {
        super(menuRepository, restaurantRepository, userRepository);
    }
}