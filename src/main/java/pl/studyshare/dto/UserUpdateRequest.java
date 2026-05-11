package pl.studyshare.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(min = 3, max = 20) @Pattern(regexp = "[A-Z][a-z]*") String firstName,
        @Size(min = 3, max = 50) @Pattern(regexp = "[A-Z][a-z]*") String lastName,
        @Min(18) Integer age
) {}