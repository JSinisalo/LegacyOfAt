package com.hert.legacyofatbackend;

import com.hert.legacyofatbackend.api.Gacha;
import com.hert.legacyofatbackend.db.GuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The backend for LegacyOfAt, the best mobile game that has ever been and will be made.
 */
@SpringBootApplication
public class LegacyofatbackendApplication implements CommandLineRunner {

    @Autowired
    private GuserRepository guserRepository;

    /**
     * The entry point of application. Initializes the gacha.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

		Gacha.init();
		SpringApplication.run(LegacyofatbackendApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {


    }
}
