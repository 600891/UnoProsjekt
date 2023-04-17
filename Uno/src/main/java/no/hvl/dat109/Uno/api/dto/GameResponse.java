package no.hvl.dat109.Uno.api.dto;

import java.util.List;

public record GameResponse(
        String gameId,
        String gameCreator,
        List<String> gameParticipants
) { }
