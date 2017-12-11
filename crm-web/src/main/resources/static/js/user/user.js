var userObj = {
    userManageTable:null,
    //初始化用户管理页面
    init:function () {
        $("#status").select2({});
    },
    //初始化用户管理表格
    initUserManageTable:function () {
        if(this.userManageTable!=null){
            this.userManageTable.draw();
        }else{
            this.userManageTable = $('#userTable').DataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
                "scrollY": "300px",
                columns: [
                    {
                        className: "td-checkbox",
                        data: "id",
                        render: function (data, type, row, meta) {
                            var value=row.userId;
                            var content = '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">';
                            content += '    <input type="checkbox" class="group-checkable" value="' + value + '" />';
                            content += '    <span></span>';
                            content += '</label>';
                            return content;
                        }
                    },
                    {
                        data: "userName"
                    }, {
                        data: "nickName"
                    }, {
                        data: "status",
                        render:function (data,type,row,meta) {
                            return data;
                        }
                    }, {
                        data: "createDate",
                        render: function (data, type, row, meta) {
                            return data;
                        }
                    }, {
                        data: "updateDate"
                    }
                ],
                ajax: {
                    url: '/user',
                    type: 'GET',
                    data: function (data, settings) {
                        //查询传递参数
                        var params = userObj.getParamData();
                        $.extend(true, data, params);
                    }
                }
            }));
        }
    },
    //获取查询参数
    getParamData:function () {
        return null;
    },
    //查询按钮
    doSearch:function () {
        this.initUserManageTable();
    }
};
$(function () {
   userObj.init();
});