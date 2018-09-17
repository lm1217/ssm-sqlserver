import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.common.utils.DateUtil;
import com.iyeed.core.entity.form.BdFormImage;
import com.iyeed.core.entity.form.BdFormSku;
import com.iyeed.core.entity.form.vo.SaveApplyForm;
import com.iyeed.core.entity.stock.BdStockInv;
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
    public void testDateTime() {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(new Date(Long.valueOf("1534738872000")));
        System.out.println("dateStr = " + dateStr);
    }
}
