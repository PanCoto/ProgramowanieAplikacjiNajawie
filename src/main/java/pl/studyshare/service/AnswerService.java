package pl.studyshare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.studyshare.domain.Answer;
import pl.studyshare.repository.AnswerRepository;
import pl.studyshare.domain.User;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService
{
    private final AnswerRepository answerRepository;

    public List<Answer> findByTaskId(Long taskId)
    {
        return answerRepository.findByTaskId(taskId);
    }

    @Transactional
    public Answer saveAnswer(Answer answer)
    {
        if (answer.getId() == null)
        {
            answer.setCreatedDate(LocalDate.now());
        }
        return answerRepository.save(answer);
    }

    @Transactional
    public void deleteAnswer(Long id)
    {
        answerRepository.deleteById(id);
    }
}
