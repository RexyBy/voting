package by.rexy.voting.web.restaurant;

import by.rexy.voting.model.Restaurant;
import by.rexy.voting.repository.RestaurantRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProfileRestController extends AbstractRestaurantController {
    static final String REST_URL = "/rest/profile/restaurants";

    public RestaurantProfileRestController(RestaurantRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Restaurant> getAllForCurrentDate() {
        return super.getAllForDate(getCurrentDate());
    }

    @GetMapping("/{id}")
    public Restaurant getOneForCurrentDate(@PathVariable int id) {
        return super.getOneForDate(id, getCurrentDate());
    }

    @GetMapping("/history")
    public List<Restaurant> getAll(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return super.getAll(startDate, endDate);
    }

    @GetMapping("/history/{id}")
    public Restaurant getOne(@PathVariable int id) {
        return super.get(id);
    }

    private LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}