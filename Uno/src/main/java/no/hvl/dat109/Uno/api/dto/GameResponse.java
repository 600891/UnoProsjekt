package no.hvl.dat109.Uno.api.dto;

import java.util.List;

public record GameResponse(
        Long gameId,
        String gameCreator,
        List<String> gameParticipants
) { }
