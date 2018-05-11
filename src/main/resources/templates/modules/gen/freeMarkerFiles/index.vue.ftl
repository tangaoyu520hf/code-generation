<template>
    <div>
        <div class="filter-container">
            <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="企业名称"
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
            <el-form :rules="rules" ref="dataForm" :model="formData" label-position="left" label-width="80px"
                     style='width: 400px; margin-left:50px;'>
            <#list table.columnList as c>
                <#if c.isNotBaseField >
                    <el-form-item :label="labels.${c.simpleJavaField}" prop="${c.simpleJavaField}">
                        <el-input v-model="formData.${c.simpleJavaField}"></el-input>
                    </el-form-item>
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
        data() {
            return {
                dataList: [],
                deleteRows: [],
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
                    currentPage: 1,
                    total: 0,
                    pageSize: 10,
                    pageSizes: [10, 20, 30, 50],
                    layout: 'total, sizes, prev, pager, next, jumper',
                    searchData: {
                        name: null,
                        code: ''
                    },
                },
                orders: {
                    order: '',
                    orderByField: ''
                },
                listLoading: true,
                dialogFormVisible: false,
                dialogStatus: '',
                textMap: {
                    update: '编辑',
                    create: '创建'
                },
                dialogPvVisible: false,
                pvData: [],

            }
        },
        created() {
            this.getList()
        },
        methods: {
            getList() {
                this.listLoading = true;
                const data = {
                    current: this.paginations.currentPage,
                    size: this.paginations.pageSize,
                    ...this.paginations.searchData,
                    ...this.orders
                };
                this.$http.get('/api/${table.className?uncap_first}/page', {params: data}).then(response => {
                    this.dataList = response.data.records;
                    this.paginations.total = response.data.total;
                    this.listLoading = false;
                });
            },
            handleFilter() {
                this.paginations.currentPage = 1
                this.getList()
            },
            handleSizeChange(val) {
                this.paginations.pageSize = val
                this.getList()
            },
            handleCurrentChange(val) {
                this.paginations.currentPage = val
                this.getList()
            },
            handleModifyStatus(row, status) {
                this.$message({
                    message: '操作成功',
                    type: 'success'
                })
                row.status = status
            },
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
                        this.$http.post('/api/${table.className?uncap_first}', this.formData, {isNotify: true}).then(() => {
                            this.dialogFormVisible = false;
                            this.getList();
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
                        this.$http.put('/api/${table.className?uncap_first}', this.formData).then(() => {
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
                    this.$http.delete('/api/${table.className?uncap_first}', {data:ids}).then(() => {
                        this.getList();
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            sortChange(column) {
                this.orders.order = constant.ORDER[column.order];
                this.orders.orderByField = column.prop;
                this.getList();
            },
            handleSelectionChange(val) {
                this.deleteRows = val;
            }
        }
    }
</script>
