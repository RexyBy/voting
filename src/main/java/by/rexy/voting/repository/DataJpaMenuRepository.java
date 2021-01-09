package by.rexy.voting.repository;

import by.rexy.voting.model.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@AllArgsConstructor
public class DataJpaMenuRepository {
    private CrudMenuRepository repository;

    public Menu getById(int id) {
        return repository.getOne(id);
    }

    public List<Menu> getAll() {
        return repository.findAll();
    }

    public List<Menu> getAllForRestaurantOnDate(int restaurantId, LocalDate date) {
        return repository.getAllForRestaurantOnDate(restaurantId, date);
    }

    public List<Menu> getAllForRestaurant(int restaurantId) {
        return repository.getAllForRestaurant(restaurantId);
    }

    public Menu getOneForRestaurantOnDate(int restaurantId, LocalDate date, int id) {
        return repository.getOneForRestaurantOnDate(restaurantId, date, id);
    }

    public Menu get(int id) {
        return repository.findById(id).orElse(null);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public Menu save(Menu menu) {
        return repository.save(menu);
    }

    public boolean vote(int menuId) {
        return repository.vote(menuId) != 0;
    }

    public boolean withdrawVote(int menuId) {
        return repository.withdrawVote(menuId) != 0;
    }
}
