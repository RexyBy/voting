package by.rexy.voting.web.user;

import by.rexy.voting.model.User;
import by.rexy.voting.repository.DataJpaUserRepository;
import by.rexy.voting.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;

import java.util.List;

public abstract class AbstractUserRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected DataJpaUserRepository repository;

    public AbstractUserRestController(DataJpaUserRepository repository) {
        this.repository = repository;
    }

    @Cacheable("users")
    public List<User> getAll() {
        log.info("get all users");
        return repository.getAll();
    }

    public User get(int id) {
        log.info("get user with id {}", id);
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        log.info("create user {}", user);
        Assert.notNull(user, "user mustn't be null");
        ValidationUtil.checkNew(user);
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user, int id) {
        Assert.notNull(user, "user mustn't be null");
        ValidationUtil.assureIdConsistent(user, id);
        log.info("update user with id {}", id);
        repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        log.info("delete user with id {}", id);
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }
}
