package no.hvl.dat109.Uno.api.dto;

import no.hvl.dat109.Uno.enums.LobbyEvent;

public record LobbyEventResponse(
        String gameId,
        String username,
        LobbyEvent event
) { }
