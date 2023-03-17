package no.hvl.dat109.Uno.utils;

import no.hvl.dat109.Uno.utils.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class.
 * @author Oda Bastesen Storebo
 */
@Service
public class DatabaseService {

    @Autowired
    private PlayerRepo playerRepo;

}
