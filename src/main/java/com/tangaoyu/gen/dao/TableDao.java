package com.tangaoyu.gen.dao;

import com.tangaoyu.gen.model.Table;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tangaoyu.gen.model.TableColumn;

import java.util.List;

/**
 * <p>
  * 业务表 Mapper 接口
 * </p>
 *
 * @author tangaoyu
 * @since 2017-05-11
 */
public interface TableDao extends BaseMapper<Table> {

    List<Table> findTableListFormDb(Table table);

    List<String> findTablePK(Table table);
}