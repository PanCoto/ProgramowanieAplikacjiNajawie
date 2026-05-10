package pl.studyshare.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import pl.studyshare.enums.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max=20)
    @Pattern(regexp = "[A-Z][a-z]*", message = "Imię musi zaczynać się wielką literą")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "[A-Z][a-z]*", message = "Nazwisko musi zaczynać się wielką literą")
    private String lastName;

    @NotBlank @Size(min = 3)
    @Column(unique = true)
    private String login;

    @NotBlank @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).+$",
            message="Hasło musi składać się ze znaku specjalnego, cyfry oraz małej i dużej litery")
    private String password;

    @Min(13)
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean enabled = true;

    @OneToMany(mappedBy = "author")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Answer> answers = new ArrayList<>();

}
