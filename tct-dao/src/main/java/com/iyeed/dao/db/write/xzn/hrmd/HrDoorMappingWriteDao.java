package com.iyeed.dao.db.write.xzn.hrmd;

import com.iyeed.core.entity.hrmd.DoorMapping;
import org.springframework.stereotype.Repository;

@Repository
public interface HrDoorMappingWriteDao {
    Integer insert(DoorMapping doorMapping) throws Exception;

    Integer del() throws Exception;
}
