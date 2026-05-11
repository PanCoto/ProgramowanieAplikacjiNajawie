package pl.studyshare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TaskCreateRequest (
    @NotBlank @Size(min = 5, max = 150 ) String title,
    @NotBlank @Size(min = 20, max = 5000) String content,
    String imageUrl,
    Long categoryId,
    boolean anonymous,
    List<QuestionDTO> questions
    ){}
