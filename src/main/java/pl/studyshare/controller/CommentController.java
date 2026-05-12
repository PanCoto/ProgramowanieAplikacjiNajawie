package pl.studyshare.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.studyshare.domain.Comment;
import pl.studyshare.domain.User;
import pl.studyshare.repository.AnswerRepository;
import pl.studyshare.repository.CommentRepository;
import pl.studyshare.repository.UserRepository;

import java.time.LocalDate;


@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @PostMapping("/add/{answerId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String addComment(@PathVariable Long answerId,
                             @RequestParam String content,
                             @AuthenticationPrincipal UserDetails userDetails) {

        var answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID odpowiedzi"));

        User author = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAnswer(answer);
        comment.setAuthor(author);
        comment.setCreatedDate(LocalDate.now());

        commentRepository.save(comment);

        return "redirect:/tasks/view/" + answer.getTask().getId();
    }
}
