package me.jhonnatanmesquita.mcspringbackend.config;

import me.jhonnatanmesquita.mcspringbackend.services.DBService;
import me.jhonnatanmesquita.mcspringbackend.services.EmailService;
import me.jhonnatanmesquita.mcspringbackend.services.MockMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockMailService();
    }
}
