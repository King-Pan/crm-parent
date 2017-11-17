var resultId;
var bankImportObj = {
    importBankTable: null,

    /**
     * 打开导入弹出框
     */
    openImportPage: function (tableId,fileInputId) {
        alert(basePath);
        if (null != bankImportObj.importBankTable) {
            bankImportObj.importBankTable.draw();
        } else {
            bankImportObj.importBankTable = $('#'+tableId).DataTable({
                ordering: false,
                "lengthChange": false,
                language: CONSTANT.DATA_TABLES.DEFAULT_OPTION.LANGUAGE,
                columns: [
                    {
                        data: "serialId",
                        ordering: false
                    },
                    {
                        data: "bankAcc"
                    }, {
                        data: "accName"
                    }, {
                        data: "bankName"
                    }, {
                        data: "oppAccNo"
                    }, {
                        data: "oppAccName"
                    }, {
                        data: "oppAccBank"
                    }, {
                        data: "cdSign"
                    }, {
                        data: "amt"
                    }, {
                        data: "abs"
                    }, {
                        data: "transTime"
                    }, {
                        data: "voucherNo"
                    }, {
                        data: "voucherType"
                    }
                ],
                ajax: {
                    url: basePath + 'bank/upload/importList',
                    type: 'POST',
                    data: function (data, settings) {
                        //查询传递参数
                        data.resultId = resultId;
                    }
                },
                "serverSide": true,//服务端分页
                "scrollY": "200px",
                "scrollCollapse": "true"
            });
        }

        $("#"+fileInputId).fileinput({
            language: 'zh', //设置语言
            uploadUrl: basePath + "bank/upload/bankExcel", //上传的地址
            allowedFileExtensions: ['xls', 'xlsx'],//接收的文件后缀
            uploadAsync: true, //默认异步上传
            showUpload: true, //是否显示上传按钮
            showRemove: true, //显示移除按钮
            showPreview: false, //是否显示预览
            showCaption: true,//是否显示标题
            enctype: 'multipart/form-data',
            validateInitialCount: true,
            uploadExtraData: function (previewId, index) {   //额外参数的关键点
                var obj = {};
                return obj;
            }
        }).on("fileuploaded", function (event, data, previewId, index) {
            var status = data.response.status;
            if (status == 200) {
                resultId = data.response.resultId;
                var params = {};
                params.resultId = resultId;
                bankImportObj.importBankTable.draw();
            } else {
                layer.msg("导入失败");
            }
        });
    },
    /**
     * 数据导入功能
     */
    bindImportBtn:function (btnId) {
        $("#" + btnId).click(function () {
            bankImportObj.importBankData();
        });
    },
    importBankData:function (url,callback) {
        var loading = layer.load(2);
        $.post(basePath + url, {resultId: resultId}, function (res) {
            layer.close(loading);
            if (res.status == 200) {
                layer.msg("导入成功");
                $('#importModal').modal('hide');
                resultId = "";
            } else {
                layer.msg("导入失败,错误信息为:\t\n" + res.message);
            }
            if(callback){
                callback(res);
            }
        });
    }
};