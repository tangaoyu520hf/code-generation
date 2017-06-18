package com.tangaoyu.gen.service.impl;

import com.tangaoyu.gen.config.Constant;
import com.tangaoyu.gen.config.datasource.TargetDataSource;
import com.tangaoyu.gen.model.Table;
import com.tangaoyu.gen.model.TableColumn;
import com.tangaoyu.gen.dao.TableColumnDao;
import com.tangaoyu.gen.service.TableColumnService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务表字段 服务实现类
 * </p>
 *
 * @author tangaoyu
 * @since 2017-05-11
 */
@Service
public class TableColumnServiceImpl extends ServiceImpl<TableColumnDao, TableColumn> implements TableColumnService {

    @Override
    @TargetDataSource(name = Constant.DATASOURCE_NAME)
    public List<TableColumn> findTableColumnListFromDB(Table table) {
        return baseMapper.findTableColumnListFromDB(table);
    }
}
