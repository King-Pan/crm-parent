var resourceObj = {
    sumbitType: 'post',
    //初始化用户管理页面
    init:function () {
        $("#status").select2({
            width:172,
            height:34
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
    getMenuTree:function () {
        //加载菜单树
        $.get("resource/tree", function(r){
            ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
            var node = ztree.getNodeByParam("menuId", vm.menu.parentId);
            ztree.selectNode(node);

            vm.menu.parentName = node.name;
        })
    },
    //查询按钮
    doSearch:function () {
        $('#resourceTable').bootstrapTable('refresh');
    },
    openAddDialog:function () {
        enableForm();
        $("#resourceModalLabel").html("新增资源");
        $("#addResourceForm")[0].reset();
        $("#resourceId").val('');
        $("#hiddenMethod").empty();
        $("#addResourceDialog").modal("show");
    },
    saveOrUpdate:function(){
        if(this.validate()){
            var data = this.getUpdateData();
            var url = "/resource";
            if(data && data._method==='put'){
                url = "/resource/" + data.roleId;
                this.sumbitType = 'put';
            }else{
                this.sumbitType = 'post';
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
                        resourceObj.doSearch();
                        $("#addResourceDialog").modal("hide");
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
        var rows = $('#resourceTable').bootstrapTable('getSelections');
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
                    url: '/resource/deleteBatch',
                    type: 'post',
                    data: {
                        roleIds: ids.join(",")
                    },
                    dataType: 'json',
                    success: function (data) {
                        if (data.status === 0) {
                            //成功后的处理
                            toastr.success(data.msg);
                            resourceObj.doSearch();
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
        var rows = $('#resourceTable').bootstrapTable('getSelections');
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
                url: '/resource/' + id,
                type: 'delete',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.status === 0) {
                        //成功后的处理
                        toastr.success(data.msg);
                        resourceObj.doSearch();
                    } else {
                        //失败后的处理
                        toastr.warning(data.msg);
                    }
                }
            });
        });
    },
    editRow:function(id){
        var row = $('#resourceTable').bootstrapTable('getRowByUniqueId',id);
        console.log(row);
        this.setForm(row);
        $("#resourceModalLabel").html("修改资源");
        $("#addResourceDialog").modal("show");
        var $hiddenMethod = $("#hiddenMethod");
        $hiddenMethod.empty();
        $hiddenMethod.html('<input id="_method" name="_method" type="hidden" value="put" />');
    }
};
//设置表单可编辑
function enableForm(){
    $(".add-form-rule :input").removeAttr("disabled");
    $(".submit-btn").show();
}


var Menu = {
    id: "resourceTable",
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '资源ID', field: 'resourceId', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '资源名称', field: 'resourceName', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级资源', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px',formatter:function (item, index) {
                if(!item.parentName){
                    return "-";
                }
                return item.parentName;
            }},
        {title: '图标', field: 'icon', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
                return item.icon == null ? '' : '<i class="'+item.icon+' fa-lg"></i>';
            }},
        {title: '类型', field: 'resourceType', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
                if(item.resourceType === "0"){
                    return '<span class="label label-primary" style="line-height: 19px;margin-left:22px;">目录</span>';
                }
                if(item.resourceType === "1"){
                    return '<span class="label label-success" style="line-height: 19px;margin-left:22px;">菜单</span>';
                }
                if(item.resourceType === "2"){
                    return '<span class="label label-warning" style="line-height: 19px;margin-left:22px;">按钮</span>';
                }
            }},
        {title: '排序号', field: 'resourceOrder', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '菜单URL', field: 'url', align: 'center', valign: 'middle', sortable: true, width: '160px',formatter:function (item,index) {
                if(!item.url){
                    return "-";
                }
                return item.url;
            }},
        {title: '授权标识', field: 'expression', align: 'center', valign: 'middle', sortable: true,formatter:function (item,index) {
                if(!item.expression){
                    return "-";
                }
                return item.expression;
            }}];
    return columns;
};

var ztree;

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "resourceId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            name: "resourceName",
            url: "nourl"
        }
    }
};

var vm = new Vue({
    // 选项
    el:'#resourceVm',
    data:{
        showList: true,
        title: null,
        menu:{
            parentName:null,
            parentId:1,
            resourceType:1,
            orderNum:0
        }
    },
    methods:{
        getMenu: function(menuId){
            //加载菜单树
            $.get("select", function(r){
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.data);
                var node = ztree.getNodeByParam("resourceId", vm.menu.parentId);
                ztree.selectNode(node);
                vm.menu.parentName = node.resourceName;
            })
        },
        add: function(){
            vm.title = "新增";
            vm.menu = {parentName:null,parentId:1,resourceType:1,orderNum:0};
            vm.getMenu();
            $("#resourceModalLabel").html("新增资源");
            $("#addResourceForm")[0].reset();
            $("#resourceId").val('');
            $("#hiddenMethod").empty();
            $("#addResourceDialog").modal("show");
        },
        update: function () {
            var resourceId = getResourceId();
            if(resourceId == null){
                return ;
            }

            $.get(baseUrl+"resource/"+resourceId, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.menu = r.data;
                vm.getMenu();
                $("#resourceModalLabel").html("修改资源");
                $("#addResourceForm")[0].reset();
                $("#resourceId").val('');
                $("#hiddenMethod").empty();
                $("#addResourceDialog").modal("show");
            });
        },
        saveOrUpdate:function () {
            if(vm.validator()){
                return ;
            }

            var url = vm.menu.resourceId == null ? "sys/menu/save" : "sys/menu/update";
            $.ajax({
                type: "POST",
                url:  baseUrl + "resource/",
                contentType: "application/json",
                data: JSON.stringify(vm.menu),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        resourceTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.menu.parentId = node[0].resourceId;
                    vm.menu.parentName = node[0].resourceName;

                    layer.close(index);
                }
            });
        },
        validator:function () {
            return true;
        }
    }
});

function getResourceId () {
    var selected = $('#resourceTable').bootstrapTreeTable('getSelections');
    if (selected.length === 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}

$(function () {
    var table = new TreeTable(Menu.id,  baseUrl + "resource", Menu.initColumn());
    table.setExpandColumn(2);
    table.setIdField("resourceId");
    table.setCodeField("resourceId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    Menu.table = table;
    resourceObj.init();
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