package psc.bet_crawler.worker;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class GameInfo {
    public String home;
    public String guest;
    public int homeGoal;
    public int guestGoal;

    //指数可能为null
    public List<String> asianIndex;
    public List<String> goalEstimate;
    public List<String> waterMark;

    public Long start;
    public String urlIndex;

    public Set<String> hitRule = new HashSet<>();

    @Override
    public String toString() {
        String asian = "";
        if (asianIndex != null) {
            for (String a : asianIndex) {
                asian += (a + " ");
            }
        }
        String estimate = "";
        if (goalEstimate != null) {
            for (String e : goalEstimate) {
                estimate += (e + " ");
            }
        }
        String water = "";
        if (waterMark != null) {
            for (String w : waterMark) {
                water += (w + " ");
            }
        }
        String hitRules = "";
        for (String h : hitRule) {
            hitRules += (h + "");
        }
        return "Home: " + home + " Guest: " + guest + " HomeGoal: " + homeGoal + " GuestGoal: " + guestGoal + " Asian: " + asian + " Estimate: " + estimate + " Water: " + water + " UrlIndex: " + urlIndex + " HitRule: " + hitRules;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameInfo)) return false;
        GameInfo that = (GameInfo) o;
        return this.home.equals(that.home)
                && this.guest.equals(that.guest)
                && this.urlIndex.equals(that.urlIndex)
                && this.homeGoal == that.homeGoal
                && this.guestGoal == that.guestGoal
                && isEquals(this.asianIndex, that.asianIndex)
                && isEquals(this.goalEstimate, that.goalEstimate)
                && isEquals(this.waterMark, that.waterMark);
    }

    public static boolean isEquals(List<String> list1, List<String> list2) {
        if (null != list1 && null != list2) {
            if (list1.containsAll(list2) && list2.containsAll(list1)) {
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlIndex);
    }


}
