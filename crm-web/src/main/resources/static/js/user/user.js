var userObj = {
    userManageTable:null,
    //初始化用户管理页面
    init:function () {
        $("#status").select2({
            width:172,
            height:34
        });
        $('#userTable').bootstrapTable({
            url: '/user',         //请求后台的URL（*）
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
            clickToSelect: true,                //是否启用点击选中行
            height: 580,                       //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "userId",                     //每一行的唯一标识，一般为主键列
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
                    field: 'userId',
                    title: '用户ID',
                    align: 'center'
                }, {
                    field: 'userName',
                    title: '用户名',
                    align: 'center'
                }, {
                    field: 'nickName',
                    title: '昵称',
                    align: 'center'
                },{
                    field: 'status',
                    title: '状态',
                    align: 'center'
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
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.limit,   //页面大小
            page: (params.offset / params.limit),
            param: $(".form-inline").serialize()
        };
        console.log(temp);
        console.log(params);
        return temp;
    },
    //查询按钮
    doSearch:function () {
        $('#userTable').bootstrapTable('refresh');
    },
    openAddDialog:function () {
        enableForm();
        $("#addUserDialog").modal("show");
        this.saveOrUpdate();
    },
    saveOrUpdate:function () {
        this.validate();
    },
    validate:function () {
        return true;
    },
    delete:function(){

    },
    edit:function(){

    },
    deleteRow:function(id){
        console.log($('#userTable').bootstrapTable('getRowByUniqueId',id));
    },
    editRow:function(id){
        console.log($('#userTable').bootstrapTable('getRowByUniqueId',id));
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
    userObj.init();
});
function operateFormatter(value, row, index) {//赋予的参数
    return [
        '<a href="javascript:void(0);" class="btn btn-warning btn-xs" onclick="userObj.editRow('+row.userId+')"><i class="icon-pencil icon-large"></i>修改</a>&nbsp;',
        '<a href="javascript:void(0);" class="btn btn-danger btn-xs" onclick="userObj.deleteRow('+row.userId+')"><i class="icon-trash icon-large"></i>删除</a>&nbsp;'
    ].join('');
}