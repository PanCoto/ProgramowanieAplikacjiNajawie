package pl.studyshare.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.studyshare.domain.Task;
import pl.studyshare.enums.TaskStatus;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>,
        JpaSpecificationExecutor<Task>
{
    List<Task> findTop10ByStatusOrderByCreatedDateDesc(TaskStatus status);
}
