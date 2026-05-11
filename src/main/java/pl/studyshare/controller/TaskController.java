package pl.studyshare.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.studyshare.domain.Task;
import pl.studyshare.domain.User;
import pl.studyshare.repository.UserRepository;
import pl.studyshare.service.CategoryService;
import pl.studyshare.service.TaskService;
import java.util.Arrays;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController
{
    private final TaskService taskService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    @GetMapping
    public String listTask
            (
                    @RequestParam(required = false) Long categoryId,
                    @RequestParam(required = false) Integer days,
                    @RequestParam(defaultValue = "0") int page,
                    HttpServletRequest request,
                    Model model
            )
    {
        String sortData = "createdDate:DESC";
        if (request.getCookies() != null)
        {
            sortData = Arrays.stream(request.getCookies())
                    .filter(c -> "sortPrefs".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst().orElse("createdDate:DESC");
        }
        String[] parts = sortData.split(":");
        Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        Pageable pageable = PageRequest.of(page, 10, sort);

        Specification<Task> spec = Specification.where(TaskService.isApproved())
                .and(TaskService.hasCategory(categoryId))
                .and(TaskService.hasRecentDate(days));
        model.addAttribute("tasks", taskService.findAll(spec, pageable));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedSort", sortData);
        return "task-list";
    }

    @GetMapping("/create")
    public String createTask(@Valid @ModelAttribute("task") Task task,
                             BindingResult result,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("categories", categoryService.findAll());
            return "task-form";
        }
        User author = userRepository.findByLogin(userDetails.getUsername()).orElseThrow();
        task.setAuthor(author);
        taskService.saveTask(task);
        return "redirect:/tasks";
    }
    @GetMapping("/{id}")
    public String viewTask(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        return "task-detail";
    }

    @PostMapping("/set-sort")
    public String setSort(@RequestParam String sortField, @RequestParam String direction, HttpServletResponse response) {
        Cookie cookie = new Cookie("sortPrefs", sortField + ":" + direction);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 24 * 60 * 60);
        response.addCookie(cookie);
        return "redirect:/tasks";
    }
}
