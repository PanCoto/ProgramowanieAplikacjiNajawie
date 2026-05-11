package pl.studyshare.dto;

import pl.studyshare.enums.TaskStatus;

public record TaskUpdateRequest(
        String title,
        String content,
        String imageUrl,
        TaskStatus status,
        Long categoryId
) {}