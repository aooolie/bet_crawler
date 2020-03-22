package psc.bet_crawler.worker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static psc.bet_crawler.worker.HttpUtils.interfaceUtil;

public class ParseGames {


    public static void main(String[] args) {

       String html = interfaceUtil("http://m.win007.com/Schedule.htm", "");//get请求


        String pattern = "18\\d{5}";


        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(html);

        while (m.find()) {
            System.out.println("http://m.win007.com/Analy/ShiJian/" + m.group(0) + ".htm");


        }

    }

}
