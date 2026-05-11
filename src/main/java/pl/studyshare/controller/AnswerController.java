package pl.studyshare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.studyshare.domain.Answer;
import pl.studyshare.domain.Task;
import pl.studyshare.domain.User;
import pl.studyshare.repository.UserRepository;
import pl.studyshare.service.AnswerService;
import pl.studyshare.service.TaskService;

@Controller
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final TaskService taskService;
    private final UserRepository userRepository;

    @PostMapping("/add/{taskId}")
    public String addAnswer(@PathVariable Long taskId,
                            @RequestParam String content,
                            @RequestParam(defaultValue = "false") boolean anonymous,
                            @AuthenticationPrincipal UserDetails userDetails) {

        Task task = taskService.findById(taskId);
        User author = userRepository.findByLogin(userDetails.getUsername()).orElseThrow();

        Answer answer = new Answer();
        answer.setContent(content);
        answer.setAnonymous(anonymous);
        answer.setTask(task);
        answer.setAuthor(author);
        answer.setUpvotes(0);
        answer.setDownvotes(0);
        answer.setIsOfficial(false);

        answerService.saveAnswer(answer);

        return "redirect:/tasks/" + taskId;
    }

    @PostMapping("/delete/{id}")
    public String deleteAnswer(@PathVariable Long id) {
        Answer answer = answerService.saveAnswer(answerService.saveAnswer(null));
        Long taskId = answer.getTask().getId();
        answerService.deleteAnswer(id);
        return "redirect:/tasks/" + taskId;
    }
}