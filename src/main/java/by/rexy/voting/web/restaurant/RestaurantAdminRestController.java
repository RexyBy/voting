package by.rexy.voting.web.restaurant;

import by.rexy.voting.model.Restaurant;
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
@RequestMapping(RestaurantAdminRestController.REST_URL)
public class RestaurantAdminRestController extends AbstractRestaurantController {
    static final String REST_URL = "/rest/admin/restaurants";

    @GetMapping
    public List<Restaurant> getAllForDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getAllForDate(date);
    }

    @GetMapping("/{id}")
    public Restaurant getOneForCurrentDate(@PathVariable int id,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getOneForDate(id, date);
    }

    @GetMapping("/history")
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping("/history/{id}")
    public Restaurant getOne(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createAndGetLocation(@RequestBody @Valid Restaurant restaurant){
        Restaurant createdRestaurant = super.create(restaurant);
        URI createdRestaurantUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(REST_URL + "/{id}")
                .buildAndExpand(createdRestaurant.getId())
                .toUri();
        return ResponseEntity.created(createdRestaurantUri).body(createdRestaurant);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid Restaurant restaurant, @PathVariable int id){
        super.update(restaurant, id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        super.delete(id);
    }
}