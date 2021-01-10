package by.rexy.voting.model;

import by.rexy.voting.to.UserTo;
import by.rexy.voting.util.JsonDeserializers;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends AbstractEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "email", nullable = false)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 8, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonDeserialize(using = JsonDeserializers.PasswordDeserializer.class)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "last_time_voted")
    private LocalDateTime lastTimeVoted;

    @ManyToOne
    private Restaurant votedRestaurant;

    public User(UserTo userTo) {
        this.name = userTo.getName();
        this.email = userTo.getEmail();
        this.password = userTo.getPassword();
        this.roles = userTo.getRoles() == null ? Set.of(Role.USER) : userTo.getRoles();
    }
}