package com.iyeed.dao.db.write.xzn.hrmd;

import com.iyeed.core.entity.hrmd.UserMapping;
import org.springframework.stereotype.Repository;

@Repository
public interface HrUserMappingWriteDao {
    Integer insert(UserMapping userMapping) throws Exception;

    Integer del() throws Exception;
}
