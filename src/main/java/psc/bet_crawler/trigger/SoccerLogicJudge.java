package psc.bet_crawler.trigger;

import psc.bet_crawler.worker.GameInfo;

public class SoccerLogicJudge {
    public static boolean judge(GameInfo info) {

        SoccerLogic teamOne = new SoccerLogic("中国");
        SoccerLogic teamTwo = new SoccerLogic("巴西");

        teamOne.soccerOwnership("主队");
        teamOne.soccerScore(8);
        teamOne.soccerAsiaIndex(-1);
        teamOne.soccerStage(2.5);
        teamOne.soccerOdds(0.6);

        teamTwo.soccerOwnership("客队");
        teamTwo.soccerScore(0);
        teamTwo.soccerAsiaIndex(-1);
        teamTwo.soccerStage(2.5);
        teamTwo.soccerOdds(0.6);

        if (teamOne.soccerAsiaIndex(1) > 0) {
            if (teamOne.soccerScore(2) == 2 && teamTwo.soccerScore(0) == 0) {
                System.out.println("比分2:0");
                if (teamOne.soccerStage(2.5) == 2.5 && teamOne.soccerOdds(0.6) >= 0.6) {
                    System.out.println("水位2.5，大小指赔率:" + teamOne.soccerOdds(0.6));
                    System.out.println("请关注比赛：" + teamOne.name + teamTwo.name);
                    teamOne.printSoccerLogic();
                } else {
                    System.out.println("不推送消息" + teamOne.soccerOdds(0.6));
                }

            } else {
                System.out.println("比分不是2:0，不推送消息");
            }
        } else {
            System.out.println("强队主场");
        }
        return false;
    }
}