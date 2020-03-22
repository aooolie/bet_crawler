package psc.bet_crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import psc.bet_crawler.service.GameService;

@EnableScheduling
@SpringBootApplication
public class BetCrawlerApplication {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(BetCrawlerApplication.class);
        SpringApplication.run(BetCrawlerApplication.class, args);
        log.info("Success.");
    }

}
