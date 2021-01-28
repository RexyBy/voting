package by.rexy.voting.web.dish;

import by.rexy.voting.model.Dish;
import by.rexy.voting.model.Menu;
import by.rexy.voting.repository.DishRepository;
import by.rexy.voting.repository.MenuRepository;
import by.rexy.voting.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = DishAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishAdminRestController {
    static final String REST_URL = "/rest/admin/restaurant/menu/dish";
    private final Logger log = LoggerFactory.getLogger(getClass());

    private DishRepository dishRepository;
    private MenuRepository menuRepository;

    @Cacheable("dishes")
    @GetMapping
    public List<Dish> getAll() {
        log.info("get all dishes");
        return dishRepository.findAll();
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish with id={}", id);
        return ValidationUtil.checkNotFoundWithId(dishRepository.findById(id).orElse(null), id);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @PostMapping
    public ResponseEntity<Dish> createAndGetLocation(@RequestBody @Valid Dish dish,
                                                     @RequestParam int menuId) {
        log.info("create dish {}", dish);
        Assert.notNull(dish, "dish mustn't be null");
        ValidationUtil.checkNew(dish);
        Menu menu = menuRepository.getOne(menuId);
        dish.setMenu(menu);
        Dish createdDish = dishRepository.save(dish);
        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(REST_URL + "/{id}")
                .buildAndExpand(createdDish.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewUser).body(createdDish);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid Dish dish, @PathVariable int id) {
        log.info("update menu with id {}", id);
        Assert.notNull(dish, "dish mustn't be null");
        ValidationUtil.assureIdConsistent(dish, id);
        Dish oldDish = dishRepository.findById(id).get();
        dish.setMenu(oldDish.getMenu());
        dishRepository.save(dish);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish with id={}", id);
        ValidationUtil.checkNotFoundWithId(dishRepository.delete(id), id);
    }
}