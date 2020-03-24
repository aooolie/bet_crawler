package psc.bet_crawler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import psc.bet_crawler.worker.GameInfo;
import psc.bet_crawler.worker.ParseDetail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static psc.bet_crawler.worker.HttpUtils.interfaceUtil;

@Service
public class GameService {
    Logger log = LoggerFactory.getLogger(GameService.class);

    public Set<String> totalUrl = new HashSet<>();

    public Set<GameInfo> gameInfos = new HashSet<>();

    public Set<GameInfo> focusGameInfos = new HashSet<>();

    public Map<String, GameInfo> informedInfos = new HashMap<>();

    //取出所有的GameInfo
    public void transformUrl2GameInfo() {
        String url = "";
        try {
            for (String index : totalUrl) {
                url = "http://m.win007.com/analy/shijian/" + index + ".htm";
                String html = interfaceUtil(url, "");//get请求
                GameInfo info = new GameInfo();

                ParseDetail.getAllIndex(html, index, info);
                ParseDetail.getScore(html, info);
                info.urlIndex = index;

                gameInfos.add(info);
            }
        } catch (Exception e) {
            log.error("[GameService] bad url: {}", url);
        }
    }

    //获取当前正在进行比赛的GameInfos
    public void getRunningGame() {

        //时间戳 单位毫秒
        Long timestamp = System.currentTimeMillis();
        for (GameInfo info : gameInfos) {
            if (info.start < timestamp && info.start > (timestamp - 7200000L)) {
                focusGameInfos.add(info);
            }
        }
    }

    //更新比赛信息
    public void updateGameInfo() {
        for (GameInfo game : focusGameInfos) {
            String url = "http://m.win007.com/analy/shijian/" + game.urlIndex + ".htm";
            String html = interfaceUtil(url, "");//get请求

            ParseDetail.getAllIndex(html, game.urlIndex, game);
            ParseDetail.getScore(html, game);

        }
    }

}
