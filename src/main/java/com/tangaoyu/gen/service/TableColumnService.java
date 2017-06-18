package com.tangaoyu.gen.service;

import com.tangaoyu.gen.model.Table;
import com.tangaoyu.gen.model.TableColumn;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 业务表字段 服务类
 * </p>
 *
 * @author tangaoyu
 * @since 2017-05-11
 */
public interface TableColumnService extends IService<TableColumn> {
    /**
     * 根据表信息查询字段
     * @param table
     * @return
     */
    List<TableColumn> findTableColumnListFromDB(Table table);
}
