package by.rexy.voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@AllArgsConstructor
public class Restaurant extends AbstractEntity {
    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String name;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Menu> menus;

    public Restaurant() {
    }
}