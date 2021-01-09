package by.rexy.voting.util;

import by.rexy.voting.model.AbstractEntity;
import by.rexy.voting.model.Restaurant;
import by.rexy.voting.model.User;
import by.rexy.voting.util.exceptions.AlreadyVotedException;
import by.rexy.voting.util.exceptions.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ValidationUtil {
    public static LocalTime revoteTimeLimit = LocalTime.of(22, 0);

    public static void checkNew(AbstractEntity entity) {
        if (!entity.isNew())
            throw new IllegalArgumentException(entity + " must be new.");
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFound(object != null, "id=" + id);
        return object;
    }

    public static Restaurant checkNotFoundRestaurantWithMenuId(Restaurant restaurant, int menuId) {
        if (restaurant == null) {
            throw new NotFoundException("Not found restaurant with menu with id=" + menuId);
        }
        return restaurant;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void canVote(User user) {
        LocalDateTime lastTimeVoted = user.getLastTimeVoted();
        if (lastTimeVoted != null
                && lastTimeVoted.toLocalDate().equals(LocalDate.now())
                && lastTimeVoted.toLocalTime().isAfter(revoteTimeLimit)) {
            throw new AlreadyVotedException(user + "has already voted today and can't re-vote.");
        }
    }

    public static void assureIdConsistent(AbstractEntity entity, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }
}