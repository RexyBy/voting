package by.rexy.voting.repository;

import by.rexy.voting.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaRestaurantRepository {
    private CrudRestaurantRepository repository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public List<Restaurant> getAllForDate(LocalDate date) {
        return repository.getAllForDate(date);
    }

    public Restaurant get(int id) {
        return repository.get(id);
    }

    public Restaurant getProxy(int id) {
        return repository.getOne(id);
    }

    public Restaurant getOneForDate(int id, LocalDate date) {
        return repository.getOneForDate(id, date);
    }

    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public Restaurant getOneByMenuId(int menuId) {
        return repository.getByMenuId(menuId);
    }
}