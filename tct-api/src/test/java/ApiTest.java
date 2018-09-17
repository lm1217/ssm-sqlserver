import com.iyeed.core.entity.form.vo.SaveApplyForm;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/16 17:35
 */
public class ApiTest {

    @Test
    public void base64Upload() {
        String url = "http://localhost:8090/tct-api/api/form/image/base64Upload.json";
        String pic = "此处为base64字符串";
        // 这一步是将base64字符串中的+号转译为%2B，避免通过http传输的时候+号变为空格
        pic = pic.replaceAll("\\+", "%2B");
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pic", pic);
        String result = httpRequest(url, paramsMap);
        System.out.println("result = " + result);
    }

    @Test
    public void imageUpload() {
        DiskFileItemFactory factory  = new DiskFileItemFactory();
        File file = factory.getRepository();
        System.out.println("file " + file.toString());
    }

    @Test
    public void testEntity() {
        SaveApplyForm form = new SaveApplyForm();
        System.out.println("form = " + form.toString());
    }

    public String httpRequest(String requestUrl, Map<String, Object> requestParamsMap) {
        StringBuffer params = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        // 组织请求参数
        Iterator<Map.Entry<String, Object>> it = requestParamsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> element = it.next();
            params.append(element.getKey());
            params.append("=");
            params.append(element.getValue());
            params.append("&");
        }
        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }
        System.out.println(params.toString());
        try {
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            //设置参数
            httpConn.setDoOutput(true);     //需要输出
            httpConn.setDoInput(true);      //需要输入
            httpConn.setUseCaches(false);   //不允许缓存
            httpConn.setRequestMethod("POST");      //设置POST方式连接

            //设置请求属性
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Charset", "UTF-8");

            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
            httpConn.connect();

            //建立输入流，向指向的URL传入参数
            DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
            dos.write(params.toString().getBytes());
            dos.flush();
            dos.close();

            //获得响应状态
            int resultCode = httpConn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine);
                }
                responseReader.close();
                System.out.println(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }
}
