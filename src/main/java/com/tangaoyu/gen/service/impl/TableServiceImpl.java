package com.tangaoyu.gen.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tangaoyu.gen.config.datasource.TargetDataSource;
import com.tangaoyu.gen.dao.TableDao;
import com.tangaoyu.gen.model.Table;
import com.tangaoyu.gen.model.TableColumn;
import com.tangaoyu.gen.service.TableColumnService;
import com.tangaoyu.gen.service.TableService;
import com.tangaoyu.gen.util.GenUtils;
import com.tangaoyu.gen.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private TableColumnService tableColumnService;
    @Override
    @TargetDataSource(name="ds1")
    public List<Table> findTableListFormDb(Table table) {
        return baseMapper.findTableListFormDb(table);
    }

    @Override
    public Table getTableAndColumnFormDb(Table table) {
        //新增
        if(StringUtils.isBlank(table.getId())) {
            List<Table> tableListFormDb = this.findTableListFormDb(table);
            if (CollectionUtils.isNotEmpty(tableListFormDb)) {
                table = tableListFormDb.get(0);
            }
            // 设置字段说明
            if (StringUtils.isBlank(table.getComments())){
                table.setComments(table.getName());
            }
            table.setClassName(StringUtils.toCapitalizeCamelCase(table.getName()));
        }else{
            table = getTableAndColumnsById(table.getId());
        }
        // 如果有表名，则获取物理表
        if (StringUtils.isNotBlank(table.getName())){
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
        return table;
    }

    @Transactional(readOnly = false)
    @Override
    public void insertAndSaveClounm(Table genTable) {
        this.insertOrUpdate(genTable);
        genTable.getColumnList().stream().forEach(tableColumn -> tableColumn.setGenTableId(genTable.getId()));
        tableColumnService.insertOrUpdateBatch(genTable.getColumnList());
    }

    /**
     * 获取表及字段相关信息
     * @param id
     * @return
     */
    @Override
    public Table getTableAndColumnsById(String id){
        Table table = this.selectById(id);
        TableColumn tableColumn = new TableColumn();
        tableColumn.setGenTableId(table.getId());
        EntityWrapper<TableColumn> tableColumnEntityWrapper = new EntityWrapper<>(tableColumn);
        tableColumnEntityWrapper.orderBy("sort",true);
        List<TableColumn> tableColumns = tableColumnService.selectList(tableColumnEntityWrapper);
        tableColumns.forEach(tableColumn1 -> tableColumn1.setGenTable(table));
        table.setColumnList(tableColumns);
        return table;
    }
}
