package by.rexy.voting.repository;

import by.rexy.voting.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId AND m.date=:date")
    List<Menu> getAllForRestaurantOnDate(int restaurantId, LocalDate date);

    @Query("SELECT m FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId AND m.date=:date")
    Menu getOneForRestaurantOnDate(int restaurantId, LocalDate date, int id);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId")
    List<Menu> getAllForRestaurant(int restaurantId);

    @Transactional
    @Modifying
    @Query("UPDATE Menu m SET m.votes=m.votes+1 WHERE m.id=:menuId")
    int vote(int menuId);

    @Transactional
    @Modifying
    @Query("UPDATE Menu m SET m.votes=m.votes-1 WHERE m.id=:menuId")
    int withdrawVote(int menuId);
}