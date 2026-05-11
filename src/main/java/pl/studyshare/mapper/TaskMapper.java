package pl.studyshare.mapper;

import org.springframework.stereotype.Component;
import pl.studyshare.domain.Task;
import pl.studyshare.dto.TaskDTO;
import pl.studyshare.enums.Role;

@Component
public class TaskMapper {
    public TaskDTO toDTO(Task task, String currentUserLogin,  Role currentUserRole)
    {
        boolean isAdmin = currentUserRole == Role.ADMIN;
        boolean isAuthor = task.getAuthor() != null && task.getAuthor().getLogin().equals(currentUserLogin);

        String authorName = ( task.getAnonymous() && !isAdmin) ? "Anonim" :
                (task.getAuthor() != null ? task.getAuthor().getLogin() : "System");

        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getContent(),
                task.getImageURL(),
                task.getStatus(),
                task.getCreatedDate(),
                authorName,
                task.getCategory() != null ? task.getCategory().getName(): "Brak",
                task.getQuestions().size(),
                task.getAnswers().size(),
                0
        );
    }
}
