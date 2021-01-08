package by.rexy.voting.web.menu;

import by.rexy.voting.model.Menu;
import by.rexy.voting.repository.DataJpaMenuRepository;
import by.rexy.voting.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractMenuController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private DataJpaMenuRepository repository;

    public List<Menu> getAll() {
        log.info("get all menus");
        return repository.getAll();
    }

    public List<Menu> getAll(int restaurantId, LocalDate date) {
        log.info("get all menus for restaurantId {}", restaurantId);
        return repository.getAllForRestaurant(restaurantId, date);
    }

    public Menu get(int id) {
        log.info("get user with id {}", id);
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public Menu create(Menu menu) {
        log.info("create menu {}", menu);
        Assert.notNull(menu, "user mustn't be null");
        ValidationUtil.checkNew(menu);
        return repository.save(menu);
    }

    public void update(Menu menu, int id) {
        Assert.notNull(menu, "menu mustn't be null");
        ValidationUtil.assureIdConsistent(menu, id);
        log.info("update menu with id {}", id);
        repository.save(menu);
    }

    public void delete(int id) {
        log.info("delete user with id {}", id);
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }
}
