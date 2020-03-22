package psc.bet_crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BetCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BetCrawlerApplication.class, args);
    }

}
