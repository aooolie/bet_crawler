package psc.bet_crawler.worker;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static psc.bet_crawler.worker.HttpUtils.interfaceUtil;

public class ParseGames {

    /**
     * 从赛程获取比赛信息
     */
    public static Set<String> getGamesSchedule() {

        Set<String> url = new HashSet<>();
        String html = interfaceUtil("http://m.win007.com/Schedule.htm", "");//get请求


        String pattern = "18\\d{5}";


        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(html);

        while (m.find()) {
//            System.out.println("http://m.win007.com/Analy/ShiJian/" + m.group(0) + ".htm");

            url.add(m.group(0));
        }

        return url;
    }

    /**
     * 从即时获取比赛信息
     */
    public static Set<String> getGamesFromImmediate() {

        Set<String> url = new HashSet<>();
        String html = interfaceUtil("http://m.win007.com/phone/Schedule_0_0.txt?flesh=0.01", "");//get请求


        String pattern = "18\\d{5}";


        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(html);

        while (m.find()) {

            url.add(m.group(0));
        }

        return url;
    }

}
