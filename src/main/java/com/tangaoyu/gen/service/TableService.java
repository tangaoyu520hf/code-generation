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

    Table getTableFormDb(Table table);
}
