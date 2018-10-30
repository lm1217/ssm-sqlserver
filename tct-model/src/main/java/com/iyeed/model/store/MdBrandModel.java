package com.iyeed.model.store;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.store.MdBrand;
import com.iyeed.model.BaseModel;
import org.springframework.stereotype.Component;

@Component
public class MdBrandModel extends BaseModel {
    
    /**
     * 根据id取得md_brand对象
     * @param  mdBrandId
     * @return
     */
    public MdBrand getMdBrandById(Integer mdBrandId) throws Exception {
    	return mdBrandWriteDao.get(mdBrandId);
    }

    public MdBrand getBrandByBrandNo(String brandNo) throws Exception {
        return mdBrandWriteDao.getBrandByBrandNo(brandNo);
    }
    
    /**
     * 保存md_brand对象
     * @param  mdBrand
     * @return
     */
    public Integer saveMdBrand(MdBrand mdBrand) throws Exception {
     	this.dbConstrains(mdBrand);
     	return mdBrandWriteDao.insert(mdBrand);
    }
     
    /**
     * 更新md_brand对象
     * @param  mdBrand
     * @return
     */
    public Integer updateMdBrand(MdBrand mdBrand) throws Exception {
        this.dbConstrains(mdBrand);
     	return mdBrandWriteDao.update(mdBrand);
    }
     
    private void dbConstrains(MdBrand mdBrand) throws Exception {
		mdBrand.setBrandNo(StringUtil.dbSafeString(mdBrand.getBrandNo(), true, 20));
		mdBrand.setBrandCode(StringUtil.dbSafeString(mdBrand.getBrandCode(), true, 255));
		mdBrand.setBrandName(StringUtil.dbSafeString(mdBrand.getBrandName(), true, 255));
		mdBrand.setBrandLogo(StringUtil.dbSafeString(mdBrand.getBrandLogo(), true, 255));
    }
}