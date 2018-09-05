<template>
    <div>
        <div class="filter-container">
            <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="名称"
                      v-model="paginations.searchData.name">
            </el-input>
            <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">搜索
            </el-button>
            <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary"
                       icon="el-icon-edit">添加
            </el-button>
            <el-button class="filter-item" :disabled="deleteRows.length<=0" style="margin-left: 10px;"
                       @click="handleDelete(deleteRows)" type="primary"
                       icon="el-icon-delete">批量删除
            </el-button>
        </div>

        <el-table :data="dataList" v-loading="listLoading" @sort-change="sortChange" element-loading-text="给我一点时间"
                  border fit
                  highlight-current-row
                  @selection-change="handleSelectionChange"
                  style="width: 100%">
            <el-table-column
                type="selection"
                align="center"
                width="45">
            </el-table-column>
            <#list table.columnList as c>
                <#if c.isNotBaseField >
            <el-table-column align="center" :label="labels.${c.simpleJavaField}"  prop="${c.simpleJavaField}">
            </el-table-column>
                </#if>
            </#list>

            <el-table-column align="center" label="操作" width="250px" class-name="small-padding">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="handleDelete([scope.row])">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div v-show="!listLoading" class="pagination-container">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                           :current-page.sync="paginations.currentPage"
                           :page-sizes="paginations.pageSizes" :page-size="paginations.pageSize"
                           :layout="paginations.layout" :total="paginations.total">
            </el-pagination>
        </div>

        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
            <el-form :rules="rules" ref="dataForm" :model="formData" label-position="left" label-width="120px">
            <#list table.columnList as c>
                <#if c.isNotBaseField >
                    <el-row>
                        <el-col :span="12">
                            <el-form-item :label="labels.${c.simpleJavaField}" prop="${c.simpleJavaField}">
                                <el-input v-model="formData.${c.simpleJavaField}"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </#if>
            </#list>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确 定</el-button>
                <el-button v-else type="primary" @click="updateData">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import paginations  from 'sendinfo-admin-ui/src/core/plugins/mixins/paginations.js'
import constantMixin from 'sendinfo-admin-ui/src/core/plugins/mixins/constant-mixins.js'
import constant from 'sendinfo-admin-ui/src/core/util/constant'

const tempformData = {
<#list table.columnList as c>
    <#if c.isNotBaseField >
    ${c.simpleJavaField}: '',
    </#if >
</#list>
}
const tempLabels = {
<#list table.columnList as c>
    <#if c.isNotBaseField >
    ${c.simpleJavaField}: '<#if c.comments??>${c.comments}</#if>',
    </#if>
</#list>
}
export default {
    created() {
        this.getListData()
    },
    dictOptions: {
    },
    name:'${table.className?uncap_first}',
    mixins: [paginations,constantMixin],
    data() {
        return {
            uri:'/api/${table.className?uncap_first}',
            formData: Object.assign({}, tempformData), // copy obj
            labels: tempLabels,
            rules: {
                <#list table.columnList as c>
                <#if c.isNotBaseField >
                ${c.simpleJavaField}: [
                <#-- 校验 主键及公共字段不需要加校验 -->
                <#if table.tableColumnPk?? && table.tableColumnPk.name != c.name >
                    <#list c.rulesList as a>
                    ${a},
                    </#list>
                </#if>
                ],
                </#if>
                </#list>
            },
            //需要给分页组件传的信息
            paginations: {
                searchData: {
                    name: null,
                    code: ''
                }
            },
            dialogFormVisible: false,
            dialogStatus: '',
            textMap: {
                update: '编辑',
                create: '创建'
            }

        }
    },
    methods: {
        resetFormData() {
            this.formData = Object.assign({}, tempformData);
        },
        handleCreate() {
            this.resetFormData()
            this.dialogStatus = 'create'
            this.dialogFormVisible = true
            this.$nextTick(() => {
                this.$refs['dataForm'].clearValidate()
            })
        },
        createData() {
            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    this.$http.post(this.uri, this.formData, {isNotify: true}).then(() => {
                        this.dialogFormVisible = false;
                        this.getListData();
                    });
                }
            })
        },
        handleUpdate(row) {
            this.formData = Object.assign({}, row) // copy obj
            this.dialogStatus = 'update'
            this.dialogFormVisible = true
            this.$nextTick(() => {
                this.$refs['dataForm'].clearValidate()
            })
        },
        updateData() {
            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    this.$http.put(this.uri, this.formData).then(() => {
                        this.dialogFormVisible = false
                        this.dataList.forEach((obj, index) => {
                            if (obj.id == this.formData.id) {
                                this.$set(this.dataList, index, this.formData)
                            }
                        });
                    });
                }
            })
        },
        handleDelete(rows) {
            this.$confirm('是否确认删除？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                const ids = rows.map((item) => {
                    return item.id;
                });
                this.$http.delete(this.uri, {data:ids}).then(() => {
                    this.getListData();
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }
    }
}
</script>
