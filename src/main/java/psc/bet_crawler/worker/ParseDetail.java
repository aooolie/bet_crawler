package psc.bet_crawler.worker;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static psc.bet_crawler.worker.HttpUtils.interfaceUtil;

public class ParseDetail {
//    public static void main(String[] args) {
//
//        String html = interfaceUtil("http://m.win007.com/analy/shijian/1861102.htm", "");//get请求
//
//        System.out.println(html);
//        GameInfo info = new GameInfo();
//        getAllIndex(html, "1861102", info);
//        getScore(html, info);
//
//        System.out.println(info);
//    }

    /**
     * 获取比分, 主客队
     */
    public static void getScore(String html, GameInfo info)  {
        try {
            //比分, 开始时间
            String patternScore = "var _scheInfo = '\\S+";
            Pattern rScore = Pattern.compile(patternScore);
            Matcher mScore = rScore.matcher(html);
            while (mScore.find()) {
                String[] name = mScore.group().split("\\^");
                String pattern = "\\d+";
                Pattern score = Pattern.compile(pattern);
                Matcher guestScore = score.matcher(name[name.length - 1]);
                Matcher homeScore = score.matcher(name[name.length - 2]);
                guestScore.find();
                homeScore.find();
                if (name[3].length() == 14) {
                    Date orderDateStart = new SimpleDateFormat("yyyyMMddHHmmss").parse(name[3]);
                    info.start = orderDateStart.getTime();
                }
                info.homeGoal = Integer.parseInt(homeScore.group());
                info.guestGoal = Integer.parseInt(guestScore.group());
            }

            //主队
            String patternHost = "var homeTeam = \"\\S+";
            Pattern rHost = Pattern.compile(patternHost);
            Matcher mHost = rHost.matcher(html);
            while (mHost.find()) {
                String[] name = mHost.group().split("\"");
                info.home = name[name.length - 2];
            }

            //客队
            String patternGuest = "(var guestTeam = \"\\S+)";
            Pattern rGuest = Pattern.compile(patternGuest);
            Matcher mGuest = rGuest.matcher(html);
            while (mGuest.find()) {
                String[] name = mGuest.group().split("\"");
                info.guest = name[name.length - 2];
            }
        } catch (Exception e) {

        }
    }

    /**
     * 获取全部指数
     */
    public static void getAllIndex(String html, String serial, GameInfo info) {
        String pattern = "var data = \"" + serial + "\\S+";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(html);

        String detail;
        while (m.find()) {
//            System.out.println("======>" + m.group(0));
            detail = m.group(0);
            String[] details = detail.split("\\$");

            List<String> trueDetails = new ArrayList<>();
            for (String s : details) {
                if (s.length() != 0) {
                    trueDetails.add(s);
                }
            }

            info.asianIndex = getAsianIndex(trueDetails.get(0));
            info.goalEstimate = getGoalEstimate(trueDetails.get(1));
            info.waterMark = getWaterMark(trueDetails.get(1));

        }
    }

    /**
     * 获取亚指
     */
    public static String[] getAsianIndex(String index) {

        List<String> res = new ArrayList<>();
        String[] totalIndex = index.split(",");
        for (String col : totalIndex) {
            String[] colIndexes = col.split("\\^");
            if (colIndexes.length > 6) {
                res.add(colIndexes[6]);
            }
        }
        return res.toArray(new String[res.size()]);
    }

    /**
     * 获取即
     */
    public static String[] getGoalEstimate(String index) {

        List<String> res = new ArrayList<>();
        String[] totalIndex = index.split(",");
        for (String col : totalIndex) {
            String[] colIndexes = col.split("\\^");
            if (colIndexes.length > 9) {
                res.add(colIndexes[9]);
            }
        }
        return res.toArray(new String[res.size()]);
    }

    /**
     * 获取水位
     */
    public static String[] getWaterMark(String index) {

        List<String> res = new ArrayList<>();
        String[] totalIndex = index.split(",");
        for (String col : totalIndex) {
            String[] colIndexes = col.split("\\^");
            if (colIndexes.length > 10) {
                res.add(colIndexes[10]);
            }
        }
        return res.toArray(new String[res.size()]);
    }
}
