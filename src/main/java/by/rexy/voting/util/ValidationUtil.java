package by.rexy.voting.util;

import by.rexy.voting.model.AbstractEntity;
import by.rexy.voting.model.Menu;
import by.rexy.voting.model.Restaurant;
import by.rexy.voting.model.User;
import by.rexy.voting.util.exceptions.ErrorType;
import by.rexy.voting.util.exceptions.NotFoundException;
import by.rexy.voting.util.exceptions.VoteException;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ValidationUtil {
    public static LocalTime revoteTimeLimit = LocalTime.of(15, 0);

    public static void checkNew(AbstractEntity entity) {
        if (!entity.isNew())
            throw new IllegalArgumentException(entity + " must be new.");
    }

    public static void checkNotFoundWithId(int affectedRows, int id) {
        checkNotFound(affectedRows != 0, "id=" + id);
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

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void canVote(User user, Menu menu) {
        LocalDateTime lastTimeVoted = user.getLastTimeVoted();
        if (lastTimeVoted != null
                && lastTimeVoted.toLocalDate().equals(LocalDate.now())
                && lastTimeVoted.toLocalTime().isAfter(revoteTimeLimit)) {
            throw new VoteException("User with id=" + user.id() + " has already voted today and can't re-vote.");
        }
        if (!menu.getDate().equals(LocalDate.now())) {
            throw new VoteException("User with id=" + user.id() + " can't vote for menu with previous date=" + menu.getDate());
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

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    public static Throwable logAndGetRootCause(Logger log, HttpServletRequest req, Exception e, boolean logStackTrace, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logStackTrace) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return rootCause;
    }
}