package psc.bet_crawler.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import psc.bet_crawler.service.GameService;
import psc.bet_crawler.trigger.SoccerLogicJudge;
import psc.bet_crawler.worker.GameInfo;
import psc.bet_crawler.worker.HttpUtils;
import psc.bet_crawler.worker.ParseDetail;
import psc.bet_crawler.worker.ParseGames;

import static psc.bet_crawler.worker.HttpUtils.interfaceUtil;

@Component
public class Scheduler {
    Logger log = LoggerFactory.getLogger(GameService.class);

    @Autowired
    GameService service;

    @Scheduled(initialDelay = 5000, fixedDelay = 3600000)
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void getGameSchedule() {
        log.info("[HourScheduler] Start hour schedule.");

        service.gameInfos.clear();
        service.totalUrl = ParseGames.getGames();
        service.transformUrl2GameInfo();
        for (GameInfo g : service.gameInfos) {
            log.info("[HourScheduler] game: {}", g);
        }
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 180000)
    public void getGameInfoSchedule() {
        log.info("[FocusScheduler] Start focus schedule.");

        service.focusGameInfos.clear();
        service.getRunningGame();
        service.updateGameInfo();
        for (GameInfo g : service.focusGameInfos) {
            log.info("[FocusScheduler] game: {}", g);
            if (SoccerLogicJudge.judge(g)) {
                HttpUtils.pushDingDing(g);
            }
        }

    }

}
