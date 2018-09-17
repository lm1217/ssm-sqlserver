package com.iyeed.model.sku;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.sku.MdSku;
import com.iyeed.dao.db.write.xzn.sku.MdSkuWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class MdSkuModel {
    
    @Resource
    private MdSkuWriteDao mdSkuWriteDao;
    
    /**
     * 根据id取得SKU表
     * @param  mdSkuId
     * @return
     */
    public MdSku getMdSkuById(Integer mdSkuId) throws Exception {
    	return mdSkuWriteDao.get(mdSkuId);
    }

    public List<MdSku> getSkuList(Map<String, Object> queryMap) throws Exception {
        return mdSkuWriteDao.getSkuList(queryMap);
    }
    
    /**
     * 保存SKU表
     * @param  mdSku
     * @return
     */
    public Integer saveMdSku(MdSku mdSku) throws Exception {
     	this.dbConstrains(mdSku);
     	return mdSkuWriteDao.insert(mdSku);
    }
     
    /**
     * 更新SKU表
     * @param  mdSku
     * @return
     */
    public Integer updateMdSku(MdSku mdSku) throws Exception {
        this.dbConstrains(mdSku);
     	return mdSkuWriteDao.update(mdSku);
    }
     
    private void dbConstrains(MdSku mdSku) throws Exception {
		mdSku.setSkuCode(StringUtil.dbSafeString(mdSku.getSkuCode(), true, 40));
		mdSku.setSkuName(StringUtil.dbSafeString(mdSku.getSkuName(), true, 200));
		mdSku.setBrandNo(StringUtil.dbSafeString(mdSku.getBrandNo(), true, 40));
		mdSku.setBrandName(StringUtil.dbSafeString(mdSku.getBrandName(), true, 40));
		mdSku.setUpdateUserNo(StringUtil.dbSafeString(mdSku.getUpdateUserNo(), true, 40));
    }
}