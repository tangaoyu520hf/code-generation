package com.tangaoyu.gen.service.impl;

import com.tangaoyu.gen.model.Table;
import com.tangaoyu.gen.dao.TableDao;
import com.tangaoyu.gen.model.TableColumn;
import com.tangaoyu.gen.service.TableService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tangaoyu.gen.util.GenUtils;
import com.tangaoyu.gen.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务表 服务实现类
 * </p>
 *
 * @author tangaoyu
 * @since 2017-05-11
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableDao, Table> implements TableService {

    @Override
    public List<Table> findTableListFormDb(Table table) {
        return baseMapper.findTableListFormDb(table);
    }

    @Override
    public Table getTableFormDb(Table table) {
        // 如果有表名，则获取物理表
        if (StringUtils.isNotBlank(table.getName())){

            List<Table> list = baseMapper.findTableListFormDb(table);
            if (list.size() > 0){

                // 如果是新增，初始化表属性d
                if (StringUtils.isBlank(table.getId())){
                    table = list.get(0);
                    // 设置字段说明
                    if (StringUtils.isBlank(table.getComments())){
                        table.setComments(table.getName());
                    }
                    table.setClassName(StringUtils.toCapitalizeCamelCase(table.getName()));
                }

                // 添加新列
                List<TableColumn> columnList = baseMapper.findTableColumnList(table);
                for (TableColumn column : columnList){
                    boolean b = false;
                    for (TableColumn e : table.getColumnList()){
                        if (e.getName().equals(column.getName())){
                            b = true;
                        }
                    }
                    if (!b){
                        table.getColumnList().add(column);
                    }
                }

                // 删除已删除的列
                for (TableColumn e : table.getColumnList()){
                    boolean b = false;
                    for (TableColumn column : columnList){
                        if (column.getName().equals(e.getName())){
                            b = true;
                        }
                    }
                    if (!b){
                        e.setDelFlag(TableColumn.DEL_FLAG_DELETE);
                    }
                }

                // 获取主键
                table.setPkList(baseMapper.findTablePK(table));

                // 初始化列属性字段
                GenUtils.initColumnField(table);

            }
        }
        return table;
    }
}
