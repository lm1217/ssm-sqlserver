package com.iyeed.service.express;

import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.express.MdExpress;

import java.util.List;
import java.util.Map;

public interface IMdExpressService {

    /**
     * 根据id取得快递公司信息表
     * @param  mdExpressId
     * @return
     */
    ServiceResult<MdExpress> getMdExpressById(Integer mdExpressId);

    ServiceResult<List<MdExpress>> getExpressList(Map<String, Object> qureyMap);
    
    /**
     * 保存快递公司信息表
     * @param  mdExpress
     * @return
     */
    ServiceResult<Integer> saveMdExpress(MdExpress mdExpress);
     
    /**
     * 更新快递公司信息表
     * @param  mdExpress
     * @return
     */
    ServiceResult<Integer> updateMdExpress(MdExpress mdExpress);
}