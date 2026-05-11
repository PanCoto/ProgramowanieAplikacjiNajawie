package pl.studyshare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.studyshare.domain.Task;
import pl.studyshare.enums.TaskStatus;
import pl.studyshare.repository.TaskRepository;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskService
{
    private final TaskRepository taskRepository;

    public Page<Task> findAll(Specification<Task> spec, Pageable pageable)
    {
        return taskRepository.findAll(spec, pageable);
    }

    public Task findById(Long id)
    {
        return taskRepository.findById(id).orElseThrow(
                () -> new jakarta.persistence.EntityNotFoundException("Zadanie jest jak mózg moniki."));
    }

    @Transactional
    public Task saveTask(Task task)
    {
        if (task.getId() == null)
        {
            task.setCreatedDate(LocalDate.now());
            if (task.getStatus() == null) task.setStatus(TaskStatus.PENDING);
        }
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id)
    {
        taskRepository.deleteById(id);
    }

    public static Specification<Task> hasRecentDate(Integer days)
    {
        return (root, query, cb) ->
        {
            if (days == null|| days <= 0) return null;
            return cb.greaterThanOrEqualTo(root.get("createdDate"), LocalDate.now().minusDays(days));
        };
    }
    public static Specification<Task> hasCategory(Long categoryId)
    {
        return (root, query, cb) ->
        {
            if (categoryId == null) return null;
            return cb.equal(root.get("category").get("id"),categoryId);
        };
    }
    public static Specification<Task> isApproved()
    {
        return (root, query, cb) -> cb.equal(root.get("status"), TaskStatus.APPROVED);
    }
}

