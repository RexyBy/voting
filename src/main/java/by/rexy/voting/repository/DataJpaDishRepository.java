package by.rexy.voting.repository;

import by.rexy.voting.model.Dish;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DataJpaDishRepository {
    private CrudDishRepository repository;

    public List<Dish> getAll() {
        return repository.findAll();
    }

    public Dish get(int id) {
        return repository.findById(id).orElse(null);
    }

    public Dish save(Dish dish) {
        return repository.save(dish);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }
}