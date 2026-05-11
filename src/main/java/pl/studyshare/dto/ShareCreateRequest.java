package pl.studyshare.dto;

import jakarta.validation.constraints.NotNull;

public record ShareCreateRequest(
        @NotNull Long taskId,
        Long recipientUserId
) {}