package by.rexy.voting.web.menu;

import by.rexy.voting.model.Menu;
import by.rexy.voting.model.Restaurant;
import by.rexy.voting.model.User;
import by.rexy.voting.repository.MenuRepository;
import by.rexy.voting.repository.RestaurantRepository;
import by.rexy.voting.repository.UserRepository;
import by.rexy.voting.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractMenuController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private MenuRepository menuRepository;
    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;

    @Cacheable("menus")
    public List<Menu> getAll() {
        log.info("get all menus");
        return menuRepository.findAll();
    }

    @Cacheable("menus")
    public List<Menu> getAllOnDate(LocalDate date) {
        log.info("get all menus on date {}", date);
        return menuRepository.getAllOnDate(date);
    }

    @Cacheable("menus")
    public List<Menu> getAllForRestaurantOnDate(int restaurantId, LocalDate date) {
        log.info("get all menus for restaurant id {} on date {}", restaurantId, date);
        return menuRepository.getAllForRestaurantOnDate(restaurantId, date);
    }

    @Cacheable("menus")
    public List<Menu> getAllForRestaurant(int restaurantId) {
        log.info("get all menus for restaurant id {}", restaurantId);
        return menuRepository.getAllForRestaurant(restaurantId);
    }

    public Menu get(int id) {
        log.info("get menu with id {}", id);
        return ValidationUtil.checkNotFoundWithId(menuRepository.findById(id).orElse(null), id);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        log.info("create menu {}", menu);
        Assert.notNull(menu, "menu mustn't be null");
        ValidationUtil.checkNew(menu);
        Restaurant restaurant = ValidationUtil.checkNotFoundWithId(restaurantRepository.getOne(restaurantId), restaurantId);
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public void update(Menu menu, int id) {
        log.info("update menu with id {}", id);
        Assert.notNull(menu, "menu mustn't be null");
        ValidationUtil.assureIdConsistent(menu, id);
        Menu oldMenu = menuRepository.getOne(id);
        menu.setRestaurant(oldMenu.getRestaurant());
        menu.setDishes(oldMenu.getDishes());
        menuRepository.save(menu);
    }

    @CacheEvict(value = "menus", allEntries = true)
    public void delete(int id) {
        log.info("delete menu with id {}", id);
        ValidationUtil.checkNotFoundWithId(menuRepository.delete(id), id);
    }

    @CacheEvict(value = "menus", allEntries = true)
    public void setVotes(int id, int votes) {
        ValidationUtil.checkNotFoundWithId(menuRepository.updateVotes(votes, id), id);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public void vote(int id, User user) {
        log.info("{} vote for menu with id={}", user, id);
        Menu votedMenu = get(id);
        ValidationUtil.canVote(user, votedMenu);
        LocalDateTime lastTimeVoted = user.getLastTimeVoted();
        Restaurant restaurant = ValidationUtil.checkNotFoundRestaurantWithMenuId(restaurantRepository.getByMenuId(id), id);
        if (lastTimeVoted != null
                && lastTimeVoted.toLocalDate().equals(LocalDate.now())
                && lastTimeVoted.toLocalTime().isBefore(ValidationUtil.revoteTimeLimit)) {
            restaurantRepository.get(user.getVotedRestaurant().getId())
                    .getMenus().stream()
                    .filter(m -> m.getDate().equals(LocalDate.now()))
                    .findFirst().ifPresent(m -> menuRepository.withdrawVote(m.id()));
        }
        user.setLastTimeVoted(LocalDateTime.now());
        user.setVotedRestaurant(restaurant);
        userRepository.save(user);
        ValidationUtil.checkNotFoundWithId(menuRepository.vote(id), id);
    }
}