/*
package com.tangaoyu.gen.common;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tangaoyu.gen.model.Table;
import com.tangaoyu.gen.model.TableColumn;
import com.tangaoyu.gen.service.TableService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

*/
/**
 * Created by tangaoyu on 2017/6/25.
 *//*

public class BaseServiceManyImpl<M extends  BaseMapper<T>,T> extends ServiceImpl<M,T> implements BaseServiceMany<T> {

    @Autowired
    TableService tableService
    @Transactional
    public boolean insertMany(Table entity) {
        this.insertOrUpdate(entity);
        List<TableColumn> columnList = entity.getColumnList();
        if(null!=columnList && columnList.size()>0){
        columnList.forEach(tableColumn -> {
            tableColumn.setGenTableId(entity.getId());
        });
        }
        tableService.insertOrUpdateBatch(columnList)
        return retBool();
    }
}
*/
