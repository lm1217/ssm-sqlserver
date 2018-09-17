package com.iyeed.model.express;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.express.MdExpress;
import com.iyeed.dao.db.write.xzn.express.MdExpressWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class MdExpressModel {
    
    @Resource
    private MdExpressWriteDao mdExpressWriteDao;
    
    /**
     * 根据id取得快递公司信息表
     * @param  mdExpressId
     * @return
     */
    public MdExpress getMdExpressById(Integer mdExpressId) throws Exception {
    	return mdExpressWriteDao.get(mdExpressId);
    }

    public List<MdExpress> getExpressList(Map<String, Object> qureyMap) throws Exception {
        return mdExpressWriteDao.getExpressList(qureyMap);
    }
    
    /**
     * 保存快递公司信息表
     * @param  mdExpress
     * @return
     */
    public Integer saveMdExpress(MdExpress mdExpress) throws Exception {
     	this.dbConstrains(mdExpress);
     	return mdExpressWriteDao.insert(mdExpress);
    }
     
    /**
     * 更新快递公司信息表
     * @param  mdExpress
     * @return
     */
    public Integer updateMdExpress(MdExpress mdExpress) throws Exception {
        this.dbConstrains(mdExpress);
     	return mdExpressWriteDao.update(mdExpress);
    }
     
    private void dbConstrains(MdExpress mdExpress) throws Exception {
		mdExpress.setExpressCode(StringUtil.dbSafeString(mdExpress.getExpressCode(), true, 40));
		mdExpress.setExpressName(StringUtil.dbSafeString(mdExpress.getExpressName(), true, 200));
    }
}