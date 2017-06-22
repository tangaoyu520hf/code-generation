package com.tangaoyu.gen.service;

import com.tangaoyu.gen.model.Table;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 业务表 服务类
 * </p>
 *
 * @author tangaoyu
 * @since 2017-05-11
 */
public interface TableService extends IService<Table> {

    List<Table> findTableListFormDb(Table table);

    /**
     * 获取表及表中行数据（从数据库中查）
     * @param table
     * @return
     */
    Table getTableAndColumnFormDb(Table table);

    /**
     * 保存table表及列到表中
     * @param genTable
     */
    void insertAndSaveClounm(Table genTable);

    /**
     * 获取表及相关字段信息
     * @param id
     * @return
     */
    Table getTableAndColumnsById(String id);


}
