var vm = new Vue({
    // 选项
    el: '#roleVm',
    data: {
        title: null,
        role: {
            roleId: null,
            roleName:'',
            roleDesc:'',
            status: 1
        },
        roleOptions:[],
        statusOptions:[
            { text: '全部', value: '-1'},
            { text: '未启用', value: '0'},
            { text: '启用', value: '1'},
            { text: '锁定', value: '2'},
            { text: '删除', value: '3'}]
    },
    methods: {
        /**
         * 查询按钮出发事件
         */
        doQuery: function () {
            $('#roleTable').bootstrapTable('refresh');
        },
        //获取查询参数
        queryParams:function (params) {

            var param = $(".form-inline").serialize();
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                size: params.limit,   //页面大小
                page: (params.offset / params.limit),
                param: decodeURIComponent(param)
            };
            return temp;
            return {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                size: params.limit,   //页面大小
                page: (params.offset / params.limit),
                status: this.$refs.status.value,
                userName: this.$refs.roleName.value
            };
        },
        saveOrUpdate:function() {
            if (vm.validated()) {
                var url = "/role";
                var type = "post";
                if (vm.role.roleId) {
                    url = "/role/" + vm.role.roleId;
                    type = 'put';
                }
                $.ajax({
                    url: url,
                    type: type,
                    dataType: 'json',
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(vm.role),
                    success: function (data) {
                        if (data.status === 0) {
                            //成功后的处理
                            toastr.success(data.msg);
                            vm.doQuery();
                            $("#addRoleDialog").modal("hide");
                        } else {
                            //失败后的处理
                            toastr.warning(data.msg);
                        }
                    }
                });
            }
        },
        validate:function () {
            if(isBlank(vm.role.roleName)){
                toastr.warning("角色名称不能为空");
                return false;
            }
            if (isBlank(vm.role.roleDesc)){
                toastr.warning("角色备注不能为空");
                return false;
            }
            return true;
        },
        /**
         * 打开新增窗口
         */
        openNewWin:function () {
            vm.title ='新增角色';
            //$("#userModalLabel").html("新增用户");
            $("#addRoleForm")[0].reset();
            $("#userId").val('');
            $("#addUserDialog").modal("show");
        },
        /**
         * 打开修改窗口
         */
        openUpdateWin:function (id) {
            vm.title ='修改角色';
            var rows;
            if(id){
                var row = $('#roleTable').bootstrapTable('getRowByUniqueId',id);
                if(row){
                    rows = [];
                    rows.push(row);
                }
            }else{
                rows = $('#roleTable').bootstrapTable('getSelections');
            }
            if(rows && rows.length===1){
                console.log(rows[0]);
                vm.role.roleId=rows[0].roleId;
                vm.role.roleName=rows[0].roleName;
                vm.role.roleDesc=rows[0].roleDesc;
                $("#addRoleDialog").modal("show");
            }else{
                toastr.warning("请选择一条需要修改的数据");
            }
        },
        operateFormatter:function (value, row, index) {
            return [
                '<a href="javascript:void(0);" class="btn btn-warning btn-xs" onclick="vm.openUpdateWin('+row.roleId+')"><i class="icon-pencil icon-large"></i>修改</a>&nbsp;',
                '<a href="javascript:void(0);" class="btn btn-danger btn-xs" onclick="vm.deleteRow('+row.roleId+')"><i class="icon-trash icon-large"></i>删除</a>&nbsp;'
            ].join('');
        },
        deleteRow:function(id){
            var ids = [];
            if(id){
                ids.push(id);
            }else{
                var rows = $('#roleTable').bootstrapTable('getSelections');
                rows.forEach(function (i) {
                    ids.push(i.roleId);
                });
            }

            if(ids && ids.length>0){
                Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
                    if (!e) {
                        return;
                    }
                    $.ajax({
                        url: '/role/deleteBatch',
                        type: 'post',
                        data: {
                            roleIds: ids.join(",")
                        },
                        dataType: 'json',
                        success: function (data) {
                            if (data.status === 0) {
                                //成功后的处理
                                toastr.success(data.msg);
                                roleObj.doSearch();
                            } else {
                                //失败后的处理
                                toastr.warning(data.msg);
                            }
                        }
                    });
                });
            }else{
                toastr.warning("请选择至少一条需要删除的数据");
            }
        }

    }
});

$(function () {
    $("#status").select2({
        width:172,
        height:34
    });
    init();
    initResourceList();
});

function initResourceList() {
    $.ajax({
        type:'get',
        url: baseUrl + '/role',
        success:function (data) {
            if(data){
                var roleData = [];
                for(var x in data.rows){
                    var obj = {
                        id: data.rows[x].roleId,
                        text: data.rows[x].roleName
                    };
                    roleData.push(obj); //这个应该是个json对象
                }
                vm.roleOptions = roleData;
                $("#roleIds").select2({
                    height:34,
                    width:'100%',
                    placeholder: '请选择用户角色',
                    data: roleData
                });
            }
        },
        error:function () {

        }
    });
}

function init() {
    $('#roleTable').bootstrapTable({
        url: '/role',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParams: this.queryParams,      //传递参数（*）
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        contentType: "application/x-www-form-urlencoded",
        strictSearch: true,
        showColumns: true,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: false,                //是否启用点击选中行
        height: 580,                       //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "roleId",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [
            {
                field:'checkbox',
                checkbox : true,
                align: 'center'
            },
            {
                field: 'roleId',
                title: '角色ID',
                align: 'center',
                visible: false
            }, {
                field: 'roleName',
                title: '角色名称',
                align: 'center'
            }, {
                field: 'roleDesc',
                title: '角色备注',
                align: 'center'
            },{
                field: 'status',
                title: '状态',
                align: 'center',
                formatter: function (data) {
                    var status;
                    if(data){
                        if(data === '0'){
                            status = '<span class="label label-info">未启用</span>';
                        }else if(data === '1'){
                            status = '<span class="label label-success">启用</span>';
                        }else if(data === '2'){
                            status = '<span class="label label-warning">锁定</span>';
                        }else if(data === '3'){
                            status = '<span class="label label-danger">删除</span>';
                        }else{
                            status = '<span class="label label-inverse">未知状态</span>';
                        }
                    }
                    return status;
                }
            },{
                field: 'createDate',
                title: '创建时间',
                align: 'center'
            },{
                field: 'updateDate',
                title: '更新时间',
                align: 'center'
            },
            {
                field: 'operate',
                title: '操作',
                align: 'center',
                formatter: operateFormatter //自定义方法，添加操作按钮
            }
        ],
        rowStyle: function (row, index) {
            var classesArr = ['default', 'info'];
            var strclass = "";
            if (index % 2 === 0) {//偶数行
                strclass = classesArr[0];
            } else {//奇数行
                strclass = classesArr[1];
            }
            return { classes: strclass };
        }//隔行变色
    });
}