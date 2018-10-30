package com.iyeed.service.store;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.store.MdBrand;

public interface IMdBrandService {

    /**
     * 根据id取得md_brand对象
     * @param  mdBrandId
     * @return
     */
    ServiceResult<MdBrand> getMdBrandById(Integer mdBrandId);

    ServiceResult<MdBrand> getBrandByBrandNo(String brandNo);

    
    /**
     * 保存md_brand对象
     * @param  mdBrand
     * @return
     */
    ServiceResult<Integer> saveMdBrand(MdBrand mdBrand);
     
    /**
     * 更新md_brand对象
     * @param  mdBrand
     * @return
     */
    ServiceResult<Integer> updateMdBrand(MdBrand mdBrand);
}