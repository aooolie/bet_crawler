package psc.bet_crawler.alert;

public class SoccerLogicTest {
	public static void main(String[] args){

		SoccerLogic teamOne = new SoccerLogic("�й�");
		SoccerLogic teamTwo = new SoccerLogic("����");
		
	    teamOne.soccerOwnership("����");
	    teamOne.soccerScore(8);
	    teamOne.soccerAsiaIndex(-1);
	    teamOne.soccerStage(2.5);
	    teamOne.soccerOdds(0.6);
	    
	    teamTwo.soccerOwnership("�Ͷ�");
	    teamTwo.soccerScore(0);
	    teamTwo.soccerAsiaIndex(-1);
	    teamTwo.soccerStage(2.5);
	    teamTwo.soccerOdds(0.6);
	    
	    if(teamOne.soccerAsiaIndex(1) > 0){
	    	if(teamOne.soccerScore(2) == 2 && teamTwo.soccerScore(0) == 0) {
	    		System.out.println("�ȷ�2:0" );
	    		if(teamOne.soccerStage(2.5) ==2.5 && teamOne.soccerOdds(0.6) >= 0.6) {
	    			System.out.println("ˮλ2.5����Сָ����:" + teamOne.soccerOdds(0.6) );
	    			System.out.println("���ע������" + teamOne.name + teamTwo.name);
	    			teamOne.printSoccerLogic();
	    		} else {
	    			System.out.println("��������Ϣ" + teamOne.soccerOdds(0.6) );
	    		}
	    		
	    	} else {
	    		System.out.println("�ȷֲ���2:0����������Ϣ" );
	    	}
	    } else {
	    	System.out.println("ǿ������" );
	    }
	
	   }
}