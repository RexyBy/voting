package by.rexy.voting.util;

import by.rexy.voting.model.AbstractEntity;
import by.rexy.voting.util.exceptions.NotFoundException;

public class ValidationUtil {
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

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
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