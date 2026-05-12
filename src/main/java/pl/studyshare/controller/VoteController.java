package pl.studyshare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.studyshare.component.VoteCollector;
import pl.studyshare.repository.AnswerRepository;


@Controller
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class VoteController {

    private final VoteCollector voteCollector;
    private final AnswerRepository answerRepository;

    @PostMapping("/{id}/vote")
    public String vote(@PathVariable Long id, @RequestParam int delta) {
        var answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID odpowiedzi: " + id));

        voteCollector.vote(id, delta);

        return "redirect:/tasks/view/" + answer.getTask().getId();
    }
}