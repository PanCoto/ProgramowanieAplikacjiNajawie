package pl.studyshare.dto;

import pl.studyshare.enums.Role;

public record UserProfileDTO(
        String firstName,
        String lastName,
        String login,
        Integer age,
        Role role
) {}