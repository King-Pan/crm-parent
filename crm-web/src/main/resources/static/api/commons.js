var CONSTANT = {
    // datatables常量
    DATA_TABLES: {
        DEFAULT_OPTION: { // DataTables初始化选项
            LANGUAGE: {
                sProcessing: "处理中...",
                sLengthMenu: "显示 _MENU_ 项结果",
                sZeroRecords: "没有匹配结果",
                sInfo: "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                sInfoEmpty: "显示第 0 至 0 项结果，共 0 项",
                sInfoFiltered: "(由 _MAX_ 项结果过滤)",
                sInfoPostFix: "",
                sSearch: "搜索:",
                searchPlaceholder: "关键字搜索",
                sUrl: "",
                sEmptyTable: "表中数据为空",
                sLoadingRecords: "载入中...",
                sInfoThousands: ",",
                oPaginate: {
                    sFirst: "首页",
                    sPrevious: "上页",
                    sNext: "下页",
                    sLast: "末页"
                },
                /*oAria: {
                    sSortAscending: ": 以升序排列此列",
                    sSortDescending: ": 以降序排列此列"
                }*/
            },
            pageLength: 50,
            // 禁用自动调整列宽
            autoWidth: false,
            // 为奇偶行加上样式，兼容不支持CSS伪类的场合
            stripeClasses: ["odd", "even"],
            // 取消默认排序查询,否则复选框一列会出现小箭头
            //order: [],
            // 隐藏加载提示,自行处理
            processing: false,
            // 启用服务器端分页
            serverSide: true,
            // 禁用原生搜索
            searching: false,
            ordering: false,
            "lengthChange": false
        },
        COLUMN: {
            // 复选框单元格
            CHECKBOX: {
                className: "td-checkbox",
                orderable: false,
                bSortable: false,
                data: "id",
                width: '30px',
                render: function (data, type, row, meta) {
                    var content = '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">';
                    content += '    <input type="checkbox" class="group-checkable" value="' + data + '" />';
                    content += '    <span></span>';
                    content += '</label>';
                    return content;
                }
            }
        },
        // 回调
        CALLBACKS: {
            // 表格绘制前的回调函数
            PREDRAWCALLBACK: function (settings) {
                if (settings.oInit.scrollX == '100%') {
                    // 给表格添加css类，处理scrollX : true出现边框问题
                    $(settings.nTableWrapper).addClass('dataTables_DTS');
                }
            },
            INITCOMPLETE: function (settings, json) {
                if (settings.oInit.scrollX == '100%' && $(settings.nTable).parent().innerWidth() - $(settings.nTable).outerWidth() > 5) {
                    $(settings.nScrollHead).children().width('100%');
                    $(settings.nTHead).parent().width('100%');
                    $(settings.nTable).width('100%');
                }
            },
            // 表格每次重绘回调函数
            DRAWCALLBACK: function (settings) {
                if ($(settings.aoHeader[0][0].cell).find(':checkbox').length > 0) {
                    // 取消全选
                    $(settings.aoHeader[0][0].cell).find(':checkbox').prop('checked', false);
                }
                // 高亮显示当前行
                $(settings.nTable).find("tbody tr").click(function (e) {
                    $(e.target).parents('table').find('tr').removeClass('warning');
                    $(e.target).parents('tr').addClass('warning');
                });
            }
        },
        // 常用render可以抽取出来，如日期时间、头像等
        RENDER: {
            ELLIPSIS: function (data, type, row, meta) {
                data = data || "";
                return '<span title="' + data + '">' + data + '</span>';
            }
        }

    }

};

if ($.fn.dataTable) {
    $.extend($.fn.dataTable.defaults, {
        processing: true,
        //order: [],
        paging: true,
        searching: false,
        language: CONSTANT.DATA_TABLES.DEFAULT_OPTION.LANGUAGE,
        preDrawCallback: CONSTANT.DATA_TABLES.CALLBACKS.PREDRAWCALLBACK,
        initComplete: CONSTANT.DATA_TABLES.CALLBACKS.INITCOMPLETE,
        drawCallback: CONSTANT.DATA_TABLES.CALLBACKS.DRAWCALLBACK
    });
}

// 屏蔽数据缺失警告
$.fn.dataTable.ext.errMode = 'none';

var utils={
    //分转元
    fenConvertYuan:function (money) {
        if(null==money||''==money){
            return 0.00;
        }
        var res=money/100.0
        return res.toFixed(2);
    },
    //元转分
    yuanConventFen:function (money) {
        if(null==money||''==money){
            return 0;
        }
        return money*100;
    },
    tmpUrl:function (url) {
        var tmp="?";
        if(url.indexOf(tmp)!=-1){
            tmp="&";
        }
        return tmp;
    },
    /**
     * 金额校验
     * @param money  金额
     * @returns {boolean} 是否为正确格式的金额，正确返回true，否则返回false
     */
    matchMoney:function (money) {
        var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
        if (reg.test(money)) {
            return true;
        }else{
            return false;
        }
    },
    /**
     * 比较2个数字的大小
     * @param n1
     * @param n2
     */
    compare:function (n1,n2) {
        return n1 < n2;
    }
};

