package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.persistence.entity.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private List<Game> notStartedGames;
    private List<Game> startedGames;

}
