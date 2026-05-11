package pl.studyshare.dto;

public record VoteResponse(
        Long answerId,
        Integer currentScore,
        Integer userVoteDeltaInSession
) {}