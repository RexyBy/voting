package by.rexy.voting.web.restaurant;


import by.rexy.voting.model.Restaurant;
import by.rexy.voting.repository.DataJpaRestaurantRepository;
import by.rexy.voting.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

public abstract class AbstractRestaurantController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataJpaRestaurantRepository repository;

    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.getAll();
    }

    public List<Restaurant> getAllForDate(LocalDate date){
        log.info("get all restaurants menu for date {}", date);
        return repository.getAllForDate(date);
    }

    public Restaurant get(int id) {
        log.info("get restaurant with id {}", id);
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public void create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        ValidationUtil.checkNew(restaurant);
        log.info("create {}", restaurant);
        repository.save(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        Assert.notNull(restaurant, "restaurant must not be null");
        ValidationUtil.assureIdConsistent(restaurant, id);
        log.info("update {} with id = {}", restaurant, id);
        ValidationUtil.checkNotFoundWithId(repository.save(restaurant), id);
    }

    public void delete(int id) {
        log.info("delete restaurant {}", id);
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }
}