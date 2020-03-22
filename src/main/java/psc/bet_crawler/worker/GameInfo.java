package psc.bet_crawler.worker;

public class GameInfo {
    public String home;
    public String guest;
    public int homeGoal;
    public int guestGoal;
    public String[] asianIndex;
    public String[] goalEstimate;
    public String[] waterMark;

    @Override
    public String toString() {
        String asian = "";
        for (String a : asianIndex) {
            asian += (a + " ");
        }
        String estimate = "";
        for (String e : goalEstimate) {
            estimate += (e + " ");
        }
        String water = "";
        for (String w : waterMark) {
            water += (w + " ");
        }
        return "home: " + home + " guest: " + guest + " homeGoal: " + homeGoal + " guestGoal: " + guestGoal + " asian: " + asian + " estimate: " + estimate + " water: " + water;
    }
}
