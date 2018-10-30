import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.common.utils.DateUtil;
import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.BdFormImage;
import com.iyeed.core.entity.form.BdFormSku;
import com.iyeed.core.entity.form.vo.SaveApplyForm;
import com.iyeed.core.entity.stock.BdStockInv;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.mail.MailInfo;
import com.iyeed.core.mail.MailSender;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/17 10:34
 */
public class CoreTest {
    @Test
    public void testBeanToJson() {
        SaveApplyForm form = new SaveApplyForm();
        List<BdFormImage> imageList = new ArrayList<>();
        List<BdFormSku> skuList = new ArrayList<>();
        BdFormImage image = new BdFormImage();
        BdFormSku sku = new BdFormSku();
        image.setImageUrl("http://www.baidu.com/common/abc.jpg");
        imageList.add(image);
        sku.setSkuCode("60QH6200001");
        sku.setSkuName("均衡粉底液611");
        sku.setStockCounterTotal(10);
        sku.setStockDepotTotal(10);
        sku.setChangeTotal(3);
        sku.setChangeType(2);
        skuList.add(sku);
        form.setFormImageList(imageList);
        form.setFormSkuList(skuList);
        form.setApplyNo("2018081710523123");
        form.setApplyDate("2018-08-17");
        form.setApplicant("刘德华");
        form.setApplicantTel("18888888888");
        form.setAllotType(1);
        form.setFormType(2);
        form.setExceptionType(1);
        form.setDestroyType(1);
        form.setStoreNo("33332123");
        form.setStoreName("上海太平洋百货有限公司 (FSS)");
        form.setReceiveStoreNo("43331122");
        form.setReceiveStoreName("上海久光百货有限公司 (FSS)");
        form.setRemark("备注信息");
        form.setOrderNo("23875983275");
        form.setExpressCode("shunfeng");
        form.setExpressName("顺丰快递");
        form.setExpressDate("2018-08-17");

        String jsonstr = JSONObject.toJSONString(form);
        System.out.println("jsonstr = " + jsonstr);
    }

    @Test
    public void testEntity() {
        BdStockInv stockInv = new BdStockInv();
        System.out.println(stockInv.toString());
    }

    @Test
    public void testAAAA() {
        List<BdFormImage> imageList = new ArrayList<>();
        BdFormImage image = new BdFormImage();
        image.setImageUrl("asdf");
        imageList.add(image);
        image = new BdFormImage();
        imageList.add(image);
        if (imageList != null) {
            for (BdFormImage img : imageList) {
                if (img.getImageUrl() == null) continue;
                System.out.println("url = " + img.getImageUrl());
            }
        }
    }

    @Test
    public void testDateTime() {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(new Date(Long.valueOf("1534738872000")));
        System.out.println("dateStr = " + dateStr);
    }





    @Test
    public void testAAA() {
        double a = 15000;
        double b = 0;
        for (int i = 0; i < 49; i++) {
            System.out.println("a = " + a);
            b = b + (a * 0.0005);//每天的利息累计
            a = a + (a * 0.0005);//每天的利息+本金 = 新的本金
            System.out.println("b = " + b);
        }
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    @Test
    public void testMailSend() {
        MailSender mailSender = MailSender.getInstance();
        MailInfo mailInfo = new MailInfo();
        mailInfo.setNotifyTo("112538201@qq.com");
        mailInfo.setSubject("java send mail test");
        mailInfo.setContent("中文编码测试，看是不是乱码");
        mailInfo.setAttachFileNames(new String[]{});//添加附件
        try {
            mailSender.sendHtmlMail(mailInfo, 3);
        } catch (Exception e) {

        }

    }

    @Test
    public void testArray() {
        Integer[] idArr = new Integer[2];
        idArr[0] = 1;
        idArr[1] = 2;
        System.out.println("aaaaaaa=" + idArr.toString());
    }
}
