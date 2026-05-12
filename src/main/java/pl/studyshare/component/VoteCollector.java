package pl.studyshare.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.studyshare.repository.AnswerRepository;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
@SessionScope
@Slf4j
public class VoteCollector implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<Long, Integer> voteDeltas = new HashMap<>();


    public void vote(Long answerId, int delta) {

        voteDeltas.put(answerId, delta);

    }

    public Integer getVoteForAnswer(Long answerId) {
        return voteDeltas.getOrDefault(answerId, 0);
    }

    @Transactional
    public void flushToDatabase(AnswerRepository answerRepository) {
        if (voteDeltas.isEmpty()) return;

        voteDeltas.forEach((answerId, delta) -> {
            if (delta != 0) {
                answerRepository.findById(answerId).ifPresent(answer -> {
                    if (delta > 0) {
                        answer.setUpvotes(answer.getUpvotes() + 1);
                    } else {
                        answer.setDownvotes(answer.getDownvotes() + 1);
                    }
                    answerRepository.save(answer);
                });
            }
        });
        voteDeltas.clear();
    }
}