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

    @Scheduled(cron = "0 15 0 * * ?")
    public void updateInformedGameSchedule() {
        log.info("[DayScheduler] update informedInfos schedule.");
        service.informedInfos.clear();
        log.info("[DayScheduler] update informedInfos schedule success.");
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 3600000)
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void getGameSchedule() {
        log.info("[HourScheduler] Start hour schedule.");

        service.gameInfos.clear();
        service.totalUrl.addAll(ParseGames.getGamesSchedule());
        service.totalUrl.addAll(ParseGames.getGamesFromImmediate());
        service.transformUrl2GameInfo();
        for (GameInfo g : service.gameInfos) {
            log.info("[HourScheduler] game: {}", g);
        }
        log.info("[HourScheduler] Hour schedule success.");
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 1200000)
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void getGameSchedule1() {
        log.info("[1/4HourScheduler] Start hour schedule.");

        service.totalUrl.addAll(ParseGames.getGamesFromImmediate());
        service.transformUrl2GameInfo();
        for (GameInfo g : service.gameInfos) {
            log.info("[1/4HourScheduler] game: {}", g);
        }
        HttpUtils.pushDingDingHeartbeat();
        log.info("[1/4HourScheduler] Hour schedule success.");

    }

    @Scheduled(initialDelay = 15000, fixedDelay = 180000)
    public void getGameInfoSchedule() {
        log.info("[FocusScheduler] Start focus schedule.");

        service.focusGameInfos.clear();
        service.getRunningGame();
        service.updateGameInfo();
        for (GameInfo g : service.focusGameInfos) {
            log.info("[FocusScheduler] game: {}", g);
            boolean trigger = false;
            if (!service.informedInfos.containsKey(g.urlIndex)) {
                log.info("[FocusScheduler] first trigger game: {}", g);
                trigger = ruleJudge(g);
                // 发到测试群
                HttpUtils.pushDingDingTest(g);
            } else {
                if(!service.informedInfos.get(g.urlIndex).equals(g)) {
                    log.info("[FocusScheduler] update trigger game: {}", g);
                    trigger = ruleJudge(g);
                    //发到测试群
                    HttpUtils.pushDingDingTest(g);
                }
            }

            service.informedInfos.put(g.urlIndex, g);

            if (trigger) {
                HttpUtils.pushDingDing(g);
            }
        }

        log.info("[FocusScheduler] Focus schedule success.");

    }

    public boolean ruleJudge(GameInfo g) {
        boolean trigger = false;

        //规则1
        if (SoccerLogicJudge.judge(g)) {
            g.hitRule.add("规则1");
            trigger = true;
        }

        return trigger;
    }
}
