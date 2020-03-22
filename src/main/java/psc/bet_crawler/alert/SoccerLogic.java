package psc.bet_crawler.alert;

public class SoccerLogic {
   String name;
   String ownership;
   int score;
   double asiaindex;
   double stage;
   double odds;
   // SoccerLogic ��Ĺ�����
   public SoccerLogic(String name){
      this.name = name;
   }
   // ����ownership��ֵ
   public void soccerOwnership(String soccerOwnership){
	  ownership =  soccerOwnership;
   }
   /* ����Score��ֵ*/
   public int soccerScore(int soccerScore){
	  score = soccerScore;
	  return score;
   }
   /* ������ָָ��Aisaindex��ֵ*/
   public double soccerAsiaIndex(double soccerAsiaIndex){
	  asiaindex = soccerAsiaIndex;
	  return asiaindex;
   }
   /* ���ô�Сָ��ˮλstage��ֵ*/
   public double soccerStage(double soccerStage){
      stage = soccerStage;
      return stage;
   }
   /* ���ô�Сָ������Odds��ֵ*/
   public double soccerOdds(double soccerOdds){
	  odds = soccerOdds;
	  return odds;
   }
   /* ��ӡ��Ϣ */
   public void printSoccerLogic(){
      System.out.println("�������:"+ name );
      System.out.println("��ӹ���:" + ownership );
      System.out.println("��Ӽ�ʱ�ȷ�:" + score );
      System.out.println("��ָ��С:" + asiaindex );
      System.out.println("��Сָˮλ:" + stage);
      System.out.println("��Сָ����:" + odds);
   }
}