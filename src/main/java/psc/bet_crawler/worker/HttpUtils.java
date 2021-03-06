package psc.bet_crawler.worker;


import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psc.bet_crawler.service.GameService;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class HttpUtils {

    Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static String interfaceUtil(String path, String data) {
        try {
            URL url = new URL(path);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;

            /**设置URLConnection的参数和普通的请求属性****start***/

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            /**设置URLConnection的参数和普通的请求属性****end***/

            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("GET");//GET和POST必须全大写
            /**GET方法请求*****start*/
            /**
             * 如果只是发送GET方式请求，使用connet方法建立和远程资源之间的实际连接即可；
             * 如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求参数。
             * */
            conn.connect();

            /**GET方法请求*****end*/

            /***POST方法请求****start*/

            /*out = new PrintWriter(conn.getOutputStream());//获取URLConnection对象对应的输出流

            out.print(data);//发送请求参数即数据

            out.flush();//缓冲数据
            */
            /***POST方法请求****end*/

            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            StringBuffer html = new StringBuffer();
            while ((str = br.readLine()) != null) {
                str = new String(str.getBytes(), "UTF-8");//解决中文乱码问题
                html.append(str);

            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
            return html.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean httpPostWithJson(String jsonObj, String url) {
        boolean isSuccess = false;

        HttpPost post = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();

            // 设置超时时间
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);

            post = new HttpPost(url);
            // 构造消息头
            post.setHeader("Content-type", "application/json");

            // 构建消息实体
            StringEntity entity = new StringEntity(jsonObj, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            post.setEntity(entity);

            HttpResponse response = httpClient.execute(post);

            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                isSuccess = false;
            } else {
                int retCode = 0;
                String sessendId = "";
                // 返回码中包含retCode及会话Id
                for (Header header : response.getAllHeaders()) {
                    if (header.getName().equals("retcode")) {
                        retCode = Integer.parseInt(header.getValue());
                    }
                    if (header.getName().equals("SessionId")) {
                        sessendId = header.getValue();
                    }
                }

                if (200 != retCode) {
                    // 日志打印
                    isSuccess = false;
                } else {
                    isSuccess = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            if (post != null) {
                try {
                    post.releaseConnection();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    public static boolean pushDingDing(GameInfo info) {
        String msg = "{\"msgtype\": \"text\", \n" +
                "        \"text\": {\n" +
                "             \"content\": \"比赛信息,\n" + info + "\"" +
                "        }\n" +
                "      }";
        String url = "https://oapi.dingtalk.com/robot/send?access_token=f59fda033ef0d4c0cd85a2e9611a4a5752c07f3959f0ff2755716d7c800b0d3c";
        return httpPostWithJson(msg, url);
    }

    public static boolean pushDingDingTest(GameInfo info) {
        String msg = "{\"msgtype\": \"text\", \n" +
                "        \"text\": {\n" +
                "             \"content\": \"比赛信息,\n" + info + "\"" +
                "        }\n" +
                "      }";
        String url = "https://oapi.dingtalk.com/robot/send?access_token=c8d8b993f2dce091d5e18881c3281e798ed9ac5b39c2e069b82569879f08429a";
        return httpPostWithJson(msg, url);
    }

    public static boolean pushDingDingHeartbeat() {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        String msg = "{\"msgtype\": \"text\", \n" +
                "        \"text\": {\n" +
                "             \"content\": \"比赛信息,发送心跳 --- " + time + "\"" +
                "        }\n" +
                "      }";
        String url = "https://oapi.dingtalk.com/robot/send?access_token=c8d8b993f2dce091d5e18881c3281e798ed9ac5b39c2e069b82569879f08429a";
        return httpPostWithJson(msg, url);
    }

}
