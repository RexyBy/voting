package by.rexy.voting.web.restaurant;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestaurantAdminRestController.REST_URL)
public class RestaurantAdminRestController extends AbstractRestaurantController {
    static final String REST_URL = "/rest/admin/restaurants";
}