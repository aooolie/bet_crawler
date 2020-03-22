package psc.bet_crawler.trigger;

import psc.bet_crawler.worker.GameInfo;

public class SoccerLogicJudge {
    public static boolean judge(GameInfo info) {

		GameInfo team = new GameInfo();

        team.home;
        team.guest;
        team.homeGoal;
        team.guestGoal;
        team.asianIndex[];
        team.goalEstimate[];
        team.waterMark[];

        if (team.asianIndex[] > 0) {
            if (team.homeGoal == 0 && team.guestGoal == 2) {
                System.out.println("强队 VS 弱队 比分0:2");
                if (team.waterMark[] == 2.5 && team.goalEstimate[] >= 0.6) {
                    return true;
                } else {
                    System.out.println("不推送消息");
					return false;
                }
            } else {
                System.out.println("比分不是2:0，不推送消息");
				return false;
            }
        } else {
            System.out.println("强队主场领先，不推送消息");
			return false;
        }
        return false;
    }
}