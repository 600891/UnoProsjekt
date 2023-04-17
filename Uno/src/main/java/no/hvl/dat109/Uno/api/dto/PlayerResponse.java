package no.hvl.dat109.Uno.api.dto;
import java.util.List;
public record PlayerResponse(
    String name,
    List<CardResponse> hand
) {}
