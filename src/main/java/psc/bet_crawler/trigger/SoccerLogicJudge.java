package psc.bet_crawler.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psc.bet_crawler.service.GameService;
import psc.bet_crawler.worker.GameInfo;

public class SoccerLogicJudge {
    public static Logger log = LoggerFactory.getLogger(SoccerLogicJudge.class);

    public static boolean judge(GameInfo info) {
        if (info.asianIndex == null || info.waterMark == null || info.goalEstimate == null) {
            return false;
        }
        Double asianIndex = Double.valueOf(info.asianIndex[info.asianIndex.length - 1]);
        Double waterMark = Double.valueOf(info.waterMark[info.waterMark.length - 1]);
        Double goalEstimate = Double.valueOf(info.goalEstimate[info.goalEstimate.length - 1]);

        if (asianIndex > 0) {
            if (info.guestGoal - info.homeGoal == 2) {
                log.info("强队 VS 弱队 分差2分: {}", info);
                if ((waterMark >= (info.guestGoal + info.homeGoal + 0.5)) && (goalEstimate >= 0.6)) {
                    return true;
                } else {
                    log.info("不推送消息: {}", info);
                    return false;
                }
            } else {
                log.info("比分不是2:0，不推送消息: {}", info);
                return false;
            }
        } else {
            log.info("强队主场领先，不推送消息: {}", info);
            return false;
        }
    }
}