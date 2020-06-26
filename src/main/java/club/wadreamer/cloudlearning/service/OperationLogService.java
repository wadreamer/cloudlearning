package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.base.BaseService;
import club.wadreamer.cloudlearning.mapper.auto.OperationLogMapper;
import club.wadreamer.cloudlearning.model.auto.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName OperationLogService
 * @Description TODO
 * @Author bear
 * @Date 2020/3/1 14:03
 * @Version 1.0
 **/
@Service
public class OperationLogService implements BaseService<OperationLog> {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertSelective(OperationLog record) {
        return operationLogMapper.insertSelective(record);
    }

    @Override
    public OperationLog selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(OperationLog record) {
        return 0;
    }
}
