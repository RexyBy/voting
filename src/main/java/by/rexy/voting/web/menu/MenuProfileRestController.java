package by.rexy.voting.web.menu;

import by.rexy.voting.model.Menu;
import by.rexy.voting.repository.DataJpaMenuRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuProfileRestController extends AbstractMenuController {
    static final String REST_URL = "/rest/profile/menu";

    public MenuProfileRestController(DataJpaMenuRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Menu> getAll(@RequestParam int restaurantId){
       return super.getAll(restaurantId, getCurrentDate());
    }

    private LocalDate getCurrentDate(){
        return LocalDate.now();
    }
}
