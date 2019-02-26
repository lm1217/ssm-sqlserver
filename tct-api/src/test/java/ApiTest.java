import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iyeed.core.entity.form.vo.SaveApplyForm;
import com.iyeed.service.ftp.IFtpService;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.client.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/16 17:35
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring-config/spring-context.xml")
public class ApiTest {
    @Resource
    private IFtpService ftpService;
    //微信的密钥固定死
    public static final String SECRET = "8cb1f566582c06a50db58f7e91542492";
    public static final String openId = "oMjKRwfar-CSBAb42UInGM-MTVfo";
    public static final String template_id = "syS6lTESVUvr_ep2EBa1o7H_SdFB0II6y8H0bEHEAC8";
    public static final String UID = "yangwu02";
    public static final String REQUEST_URL = "http://demo.iyeed.com.cn:8210/wx-api-as/service/index.shtml";
    private static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    public static final String TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    @Test
    public void testFtp() {
        ftpService.executeMdUser("E:/ftp");
    }

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

    @Test
    public void testWx() {
        System.out.println("当前时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public String httpRequest(String requestUrl, Map<String, Object> requestParamsMap) {
        StringBuffer params = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        // 组织请求参数
        Iterator<Map.Entry<String, Object>> it = requestParamsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> element = it.next();
            params.append(element.getKey());
//            params.append("=");
            params.append(element.getValue());
//            params.append("&");
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

    @Test
    public void testSign() {
        Map<String, Object> requestParamsMap = new HashMap<String, Object>();
        requestParamsMap.put("accessCode", UID);
        requestParamsMap.put("method", "iyeed.wxapi.send.templatemsg");
        requestParamsMap.put("timestamp", "2019-1-9 17:12:42");

        Map<String, Object> data = new HashMap<>();
        data.put("touser", openId);
        data.put("template_id", template_id);
        data.put("url", "");
        data.put("miniprogram", "");
        data.put("data","{}");
//        data.put("data", "{first:{value:恭喜你购买成功！,color:#173177},remark:{value:欢迎再次购买！,color:#173177}}");

        requestParamsMap.put("data", JSON.toJSONString(data));
        requestParamsMap.put("sign", sign(requestParamsMap, SECRET).toUpperCase());
        System.out.println("==========="+ requestParamsMap);

        httpRequest(REQUEST_URL, requestParamsMap);
    }

    // 密匙生成规则MD5（密匙+所传参数按字母升序+密匙）
    public static String sign(Map<String, Object> params, String secret) {
        String result = null;
        if (params == null)
            return result;

        // 将参数按key排序
        Set<String> keys = params.keySet();
        String[] ps = new String[keys.size()];
        int i = 0;
        for (String key : keys) {
            Object value = params.get(key);
            if (value != null) {
                ps[i++] = key + value.toString();
            }
        }

        if (i == 0)
            return result;
        String[] ss = new String[i];
        System.arraycopy(ps, 0, ss, 0, i);
        Arrays.sort(ss);

        // 将secret放在尾部，拼成字符串
        StringBuilder orgin = new StringBuilder();
        for (int j = 0; j < ss.length; j++) {
            if (j == ss.length - 1) {
                orgin.append(ss[j]);
            } else {
                orgin.append(ss[j]);
            }
        }
        orgin.insert(0, secret);
        orgin.append(secret);

        System.out.println("params = [" + orgin.toString() + "]");

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToString(md.digest(orgin.toString().getBytes("utf-8")));
        } catch (Exception ex) {
            throw new java.lang.RuntimeException("sign error !");
        }
        return result;
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }
}
