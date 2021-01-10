package by.rexy.voting.web.restaurant;


import by.rexy.voting.model.Restaurant;
import by.rexy.voting.repository.DataJpaRestaurantRepository;
import by.rexy.voting.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private DataJpaRestaurantRepository repository;

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.getAll();
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllForDate(LocalDate date) {
        log.info("get all restaurants menu for date {}", date);
        return repository.getAllForDate(date);
    }


    public Restaurant get(int id) {
        log.info("get restaurant with id {}", id);
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public Restaurant getOneForDate(int id, LocalDate date) {
        log.info("get restaurant with id {} for date {}", id, date);
        return ValidationUtil.checkNotFoundWithId(repository.getOneForDate(id, date), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        ValidationUtil.checkNew(restaurant);
        log.info("create {}", restaurant);
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant, int id) {
        Assert.notNull(restaurant, "restaurant must not be null");
        ValidationUtil.assureIdConsistent(restaurant, id);
        log.info("update {} with id = {}", restaurant, id);
        ValidationUtil.checkNotFoundWithId(repository.save(restaurant), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        log.info("delete restaurant {}", id);
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }
}