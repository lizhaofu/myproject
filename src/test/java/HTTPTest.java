import java.io.IOException;

/**
 * Created by lizhaofu on 2017/5/25.
 */
public class HTTPTest {
    public static void main(String[] args) {
        String st = null;
        try {
          st = HTTP.do_get("http://i.yjapi.com/ECISimple/Search?key=ApiKey&keyword=苏州朗动网络科技有限公司");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(st);
        String body =
                "{\n" +
                        "  \"Status\": \"200\",\n" +
                        "  \"Message\": \"查询成功\",\n" +
                        "  \"Result\": [\n" +
                        "    {\n" +
                        "      \"KeyNo\": \"f625a5b661058ba5082ca508f99ffe1b\",\n" +
                        "      \"Name\": \"苏州朗动网络科技有限公司\",\n" +
                        "      \"OperName\": \"陈德强\",\n" +
                        "      \"StartDate\": \"2014-03-12T00:00:00\",\n" +
                        "      \"Status\": \"在业\",\n" +
                        "      \"No\": \"91320594088140947F\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";
        String num = HTTP.getNO(body);
        System.out.println(num);

    }
}
