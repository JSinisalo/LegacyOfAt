package com.hert.legacyofatbackend;

import com.hert.legacyofatbackend.db.GuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LegacyofatbackendApplication implements CommandLineRunner {

    @Autowired
    private GuserRepository guserRepository;

	public static void main(String[] args) {
		SpringApplication.run(LegacyofatbackendApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {


    }
}
