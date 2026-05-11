package pl.studyshare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record AnswerCreateRequest(
        @NotBlank @Size(min = 10, max = 2000) String content,
        boolean anonymous
) {}