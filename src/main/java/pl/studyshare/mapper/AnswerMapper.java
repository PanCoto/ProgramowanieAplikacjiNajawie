package pl.studyshare.mapper;

import org.springframework.stereotype.Component;
import pl.studyshare.domain.Answer;
import pl.studyshare.dto.AnswerDTO;
import pl.studyshare.enums.Role;

@Component
public class AnswerMapper
{
    public AnswerDTO toDTO(Answer answer, Role currentUserRole)
    {
        String authorName = ( answer.getAnonymous() && currentUserRole != Role.ADMIN) ? "Anonim" :
            (answer.getAuthor() != null ? answer.getAuthor().getLogin() : "System");
        return new AnswerDTO(
                answer.getId(),
                answer.getContent(),
                answer.getCreatedDate(),
                answer.getScore(),
                authorName,
                answer.getIsOfficial(),
                0
        );
    }
}
