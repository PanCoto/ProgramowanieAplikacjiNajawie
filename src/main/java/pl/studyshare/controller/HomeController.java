package pl.studyshare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.studyshare.domain.Task;
import pl.studyshare.enums.TaskStatus;
import pl.studyshare.repository.TaskRepository;

@Controller
@RequiredArgsConstructor
public class HomeController
{
    private final TaskRepository taskRepository;
    @GetMapping("/")
    public String home(Model model)
    {
/*        model.addAttribute("tasks",
                taskRepository.findTop10ByStatusOrderByCreatedDateDesc(TaskStatus.APPROVED));*/
        return "home";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
}
