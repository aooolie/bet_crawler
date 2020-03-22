package psc.bet_crawler.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import psc.bet_crawler.service.GameService;
import psc.bet_crawler.worker.GameInfo;
import psc.bet_crawler.worker.ParseDetail;
import psc.bet_crawler.worker.ParseGames;

import static psc.bet_crawler.worker.HttpUtils.interfaceUtil;

@Component
public class Scheduler {

    @Autowired
    GameService service;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void getGameSchedule() {
        service.gameInfos.clear();
        service.totalUrl = ParseGames.getGames();
        service.transformUrl2GameInfo();
    }

    @Scheduled(cron = "0 0/3 * * * ?")
    public void getGameInfoSchedule() {
        service.focusGameInfos.clear();
        service.getRunningGame();
        service.updateGameInfo();
    }

}
