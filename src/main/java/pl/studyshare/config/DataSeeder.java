package pl.studyshare.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.studyshare.domain.Category;
import pl.studyshare.domain.User;
import pl.studyshare.enums.Role;
import pl.studyshare.repository.CategoryRepository;
import pl.studyshare.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {

            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("Systemu")
                    .login("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .age(30)
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();
            userRepository.save(admin);

            categoryRepository.save(new Category(null, "Programowanie aplikacji WWW w Javie", "Zadania z Javy, Python itp.", null));
            categoryRepository.save(new Category(null, "Algorytmy i struktury danych", "Złożoność, sortowanie, grafy", null));
            categoryRepository.save(new Category(null, "Systemy Baz Danych", "SQL i NoSQL", null));
        }
    }
}