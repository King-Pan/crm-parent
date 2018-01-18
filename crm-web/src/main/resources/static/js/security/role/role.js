var roleObj = {
    sumbitType: 'post',
    //初始化用户管理页面
    init:function () {
        $("#status").select2({
            width:172,
            height:34
        });
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
                                status = '未启用';
                            }else if(data === '1'){
                                status = '启用';
                            }else if(data === '2'){
                                status = '锁定';
                            }else if(data === '3'){
                                status = '删除';
                            }else{
                                status = '未知状态';
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
                var classesArr = ['success', 'info'];
                var strclass = "";
                if (index % 2 === 0) {//偶数行
                    strclass = classesArr[0];
                } else {//奇数行
                    strclass = classesArr[1];
                }
                return { classes: strclass };
            }//隔行变色
        });
    },
    //获取查询参数
    queryParams:function (params) {
        var params = $(".form-inline").serialize();
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.limit,   //页面大小
            page: (params.offset / params.limit),
            param: decodeURIComponent(params)
        };
        return temp;
    },
    //查询按钮
    doSearch:function () {
        $('#roleTable').bootstrapTable('refresh');
    },
    openAddDialog:function () {
        enableForm();
        $("#roleModalLabel").html("新增用户");
        $("#addRoleForm")[0].reset();
        $("#roleId").val('');
        $("#hiddenMethod").empty();
        $("#addRoleDialog").modal("show");
    },
    saveOrUpdate:function(){
        if(this.validate()){
            var data = this.getUpdateData();
            var url = "/role";
            if(data && data._method==='put'){
                url = "/role/" + data.roleId;
                this.sumbitType = 'put';
            }
            $.ajax({
                url: url,
                type: this.sumbitType,
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(data),
                success:function (data) {
                    if(data.status === 0){
                        //成功后的处理
                        toastr.success(data.msg);
                        roleObj.doSearch();
                        $("#addRoleDialog").modal("hide");
                    }else{
                        //失败后的处理
                        toastr.warning(data.msg);
                    }
                }
            });
        }
    },
    validate:function () {
        var data = this.getUpdateData();
        if(!data.roleName){
            toastr.warning("角色名称不能为空");
            return false;
        }
        if (!data.roleDesc){
            toastr.warning("角色备注不能为空");
            return false;
        }
        return true;
    },
    getUpdateData:function(){
        var obj ={};
        obj.roleId = $("#roleId").val();
        obj.roleName = $("#roleName").val();
        obj.roleDesc = $("#roleDesc").val();
        if(obj.roleId){
            obj._method = $("#_method").val();
        }
        return obj;
    },
    delete:function(){
        var rows = $('#roleTable').bootstrapTable('getSelections');
        if(rows && rows.length>0){
            var ids = [];
            rows.forEach(function (i) {
                ids.push(i.roleId);
            });

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
    },
    edit:function(){
        var rows = $('#roleTable').bootstrapTable('getSelections');
        if(rows && rows.length===1){
            console.log(rows[0]);
            this.editRow(rows[0].roleId);
        }else{
            toastr.warning("请选择一条需要修改的数据");
        }
    },
    setForm:function (jsonValue) {
        var obj = $("#addRoleForm");
        $.each(jsonValue, function (name, ival) {
            var $obj = obj.find("input[name=" + name + "]");
            if ($obj.attr("type") === "checkbox") {
                if (ival !== null) {
                    var checkboxObj = $("[name=" + name + "]");
                    var checkArray = ival.split(";");
                    for (var i = 0; i < checkboxObj.length; i++) {
                        for (var j = 0; j < checkArray.length; j++) {
                            if (checkboxObj[i].value === checkArray[j]) {
                                checkboxObj[i].click();
                            }
                        }
                    }
                }
            }
            else if ($obj.attr("type") === "radio") {
                $obj.each(function () {
                    var radioObj = $("[name=" + name + "]");
                    for (var i = 0; i < radioObj.length; i++) {
                        if (radioObj[i].value === ival) {
                            radioObj[i].click();
                        }
                    }
                });
            }
            else if ($obj.attr("type") === "textarea") {
                obj.find("[name=" + name + "]").html(ival);
            }
            else {
                obj.find("[name=" + name + "]").val(ival);
            }
        });
    },
    deleteRow:function(id){
        Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: '/role/' + id,
                type: 'delete',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
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
    },
    editRow:function(id){
        var row = $('#roleTable').bootstrapTable('getRowByUniqueId',id);
        console.log(row);
        this.setForm(row);
        $("#roleModalLabel").html("修改用户");
        $("#addRoleDialog").modal("show");
        var $hiddenMethod = $("#hiddenMethod");
        $hiddenMethod.empty();
        $hiddenMethod.html('<input id="_method" name="_method" type="hidden" value="put" />');
    }
};
//设置表单不可编辑
function disableForm(){
    $(".add-form-rule :input").attr("disabled","disabled");
    $(".submit-btn").hide();
}

//设置表单可编辑
function enableForm(){
    $(".add-form-rule :input").removeAttr("disabled");
    $(".submit-btn").show();
}
$(function () {
    roleObj.init();
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": true,
        "progressBar": false,
        "positionClass": "toast-top-full-width",
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
});
function operateFormatter(value, row, index) {//赋予的参数
    return [
        '<a href="javascript:void(0);" class="btn btn-warning btn-xs" onclick="roleObj.editRow('+row.roleId+')"><i class="icon-pencil icon-large"></i>修改</a>&nbsp;',
        '<a href="javascript:void(0);" class="btn btn-danger btn-xs" onclick="roleObj.deleteRow('+row.roleId+')"><i class="icon-trash icon-large"></i>删除</a>&nbsp;'
    ].join('');
}