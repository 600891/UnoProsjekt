package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.repository.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class.
 * @author Oda Bastesen Storebo
 */
@Service
public class PersistenceService {

    @Autowired
    private PlayerRepo playerRepo;

}
