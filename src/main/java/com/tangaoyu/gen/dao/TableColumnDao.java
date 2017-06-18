package com.tangaoyu.gen.dao;

import com.tangaoyu.gen.model.Table;
import com.tangaoyu.gen.model.TableColumn;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 业务表字段 Mapper 接口
 * </p>
 *
 * @author tangaoyu
 * @since 2017-05-11
 */
public interface TableColumnDao extends BaseMapper<TableColumn> {

    List<TableColumn> findTableColumnListFromDB(Table table);
}