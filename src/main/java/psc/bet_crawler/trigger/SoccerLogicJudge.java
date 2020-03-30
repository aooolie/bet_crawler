package psc.bet_crawler.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psc.bet_crawler.service.GameService;
import psc.bet_crawler.worker.GameInfo;

import java.util.ArrayList;

public class SoccerLogicJudge {
    public static Logger log = LoggerFactory.getLogger(SoccerLogicJudge.class);

    public static boolean judge(GameInfo info) {
        if (info.asianIndex == null || info.waterMark == null || info.goalEstimate == null) {
            return false;
        }
        if (info.asianIndex.size() == 0 || info.waterMark.size() == 0 || info.goalEstimate.size() == 0) {
            return false;
        }
        Double asianIndex = Double.valueOf(info.asianIndex.get(info.asianIndex.size() - 1));
        Double waterMark = Double.valueOf(info.waterMark.get(info.waterMark.size() - 1));
        Double goalEstimate = Double.valueOf(info.goalEstimate.get(info.goalEstimate.size() - 1));

        if (asianIndex > 0) {

            //TODO 修改触发规则
            if (info.guestGoal - info.homeGoal >= 1) {
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