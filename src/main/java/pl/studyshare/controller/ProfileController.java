package pl.studyshare.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.studyshare.domain.User;
import pl.studyshare.dto.UserUpdateRequest;
import pl.studyshare.repository.UserRepository;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;

    @GetMapping("/edit")
    public String editProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony"));

        UserUpdateRequest request = new UserUpdateRequest(
                user.getFirstName(),
                user.getLastName(),
                user.getAge()
        );

        model.addAttribute("userUpdateRequest", request);
        return "profile-edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@Valid @ModelAttribute("userUpdateRequest") UserUpdateRequest request,
                                BindingResult result,
                                @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "profile-edit";
        }

        User user = userRepository.findByLogin(userDetails.getUsername()).orElseThrow();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setAge(request.age());

        userRepository.save(user);
        return "redirect:/profile/edit?success";
    }
}