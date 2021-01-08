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

    public List<Menu> getAll(){
        return repository.findAll();
    }

    public List<Menu> getAllForRestaurant(int restaurantId, LocalDate date){
        return repository.getAllForRestaurant(restaurantId, date);
    }

    public Menu get(int id){
        return repository.findById(id).orElse(null);
    }

    public boolean delete(int id){
        return repository.delete(id) != 0;
    }

    public Menu save(Menu menu){
        return repository.save(menu);
    }
}
