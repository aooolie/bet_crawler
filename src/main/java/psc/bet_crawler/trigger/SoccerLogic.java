package psc.bet_crawler.trigger;

public class SoccerLogic {
   String name;
   String ownership;
   int score;
   double asiaindex;
   double stage;
   double odds;
   // SoccerLogic 类的构造器
   public SoccerLogic(String name){
      this.name = name;
   }
   // 设置ownership的值
   public void soccerOwnership(String soccerOwnership){
	  ownership =  soccerOwnership;
   }
   /* 设置Score的值*/
   public int soccerScore(int soccerScore){
	  score = soccerScore;
	  return score;
   }
   /* 设置亚指指数Aisaindex的值*/
   public double soccerAsiaIndex(double soccerAsiaIndex){
	  asiaindex = soccerAsiaIndex;
	  return asiaindex;
   }
   /* 设置大小指数水位stage的值*/
   public double soccerStage(double soccerStage){
      stage = soccerStage;
      return stage;
   }
   /* 设置大小指数赔率Odds的值*/
   public double soccerOdds(double soccerOdds){
	  odds = soccerOdds;
	  return odds;
   }
   /* 打印信息 */
   public void printSoccerLogic(){
      System.out.println("球队名字:"+ name );
      System.out.println("球队归属:" + ownership );
      System.out.println("球队即时比分:" + score );
      System.out.println("亚指大小:" + asiaindex );
      System.out.println("大小指水位:" + stage);
      System.out.println("大小指赔率:" + odds);
   }
}