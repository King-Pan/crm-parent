var departUtilObj = {
    subCode: null,
    departObj:null,
    /**
     * 初始化自营厅-营业厅下拉框
     */
    initMine:function ($id,$depart,initFunc,callback) {
        var loading = layer.load(2);
        this.initMineSub($id,initFunc);
        departUtilObj.departObj = $('#'+$depart);
        departUtilObj.departObj.prop("disabled", true);
        layer.close(loading);
        $("#"+$id).on("change", function () {
            departUtilObj.subCode = $(this).val();
            console.log(departUtilObj.subCode);
            if (departUtilObj.subCode&&departUtilObj.subCode != '-1') {
                departUtilObj.subCode = departUtilObj.subCode.trim();
                departUtilObj.departObj.prop("disabled", false);
                $.ajax({
                    url: basePath + "tdMDepart/getDepartBySubCode",
                    type: 'POST',
                    data: 'subCode=' + departUtilObj.subCode,
                    success: function (data) {
                        layer.close(loading);
                        departUtilObj.departObj.select2('destroy');
                        departUtilObj.departObj.empty();
                        if(data.success){
                            var list=data.data;
                            departUtilObj.departObj.append(' <option value="-1">全部</option>');
                            list.forEach(function (t,i) {
                                if(i!=0){
                                    departUtilObj.departObj.append(' <option value="'+t.DICTCODE+'">'+t.DICTVALUE+'</option>');
                                }
                            });
                            departUtilObj.departObj.select2({});
                        }else {
                            layer.msg("分管区域数据加载失败");
                        }
                        if(callback){
                            callback();
                        }

                    }
                });
            } else {
                $('#'+$depart).select2().val("-1").trigger("change");
                $('#'+$depart).prop("disabled", true);
            }
        });
    },
    /**
     * 自营厅区域
     */
    initMineSub:function ($id,initFunc) {
        var loading = layer.load(2);
        var $obj=$("#"+$id);
        $obj.empty();
        $obj.append(' <option value="-1">全部</option>');
        //1. 获取区域信息
        $.get(basePath+"tdMDepart/queryMySubCompany",function (res) {
            layer.close(loading);
            if(res.success){
                var list=res.data;
                list.forEach(function (t,i) {
                    if(i!=0){
                        $obj.append(' <option value="'+t.DICTCODE+'">'+t.DICTVALUE+'</option>');
                    }
                });
                if(initFunc){
                    initFunc();
                }
            }else {
                layer.msg("分管区域数据加载失败");
            }
        });
    },
    /**
     * 初始化合作厅-营业厅下拉框
     */
    initCoop:function ($id,$depart) {
        var loading = layer.load(2);
        this.initCoopSub($id);
        departUtilObj.departObj = $('#'+$depart);
        departUtilObj.departObj.prop("disabled", true);
        layer.close(loading);
        $("#"+$id).on("select2:select", function () {
            departUtilObj.subCode = $(this).val();
            if (departUtilObj.subCode != '-1') {
                departUtilObj.subCode = departUtilObj.subCode.trim();
                departUtilObj.departObj.prop("disabled", false);
                $.ajax({
                    url: basePath + "tdMDepart/getCoopDepartBySubCode",
                    type: 'POST',
                    data: 'subCode=' + departUtilObj.subCode,
                    success: function (data) {
                        layer.close(loading);
                        departUtilObj.departObj.select2('destroy');
                        departUtilObj.departObj.empty();
                        if(data.success){
                            var list=data.data;
                            departUtilObj.departObj.append(' <option value="-1">全部</option>');
                            list.forEach(function (t,i) {
                                if(i!=0){
                                    departUtilObj.departObj.append(' <option value="'+t.DICTCODE+'">'+t.DICTVALUE+'</option>');
                                }
                            });
                            departUtilObj.departObj.select2({});
                        }else {
                            layer.msg("分管区域数据加载失败");
                        }

                    }
                });
            } else {
                $('#'+$depart).select2().val("-1").trigger("change");
                $('#'+$depart).prop("disabled", true);
            }
        });
    },
    /**
     * 合作厅区域
     */
    initCoopSub:function ($id) {
        var loading = layer.load(2);
        var $obj=$("#"+$id);
        $obj.empty();
        $obj.append(' <option value="-1">全部</option>');
        //1. 获取区域信息
        $.get(basePath+"tdMDepart/getAllCoopSubCompany",function (res) {
            layer.close(loading);
            if(res.success){
                var list=res.data;
                list.forEach(function (t,i) {
                    if(i!=0){
                        $obj.append(' <option value="'+t.DICTCODE+'">'+t.DICTVALUE+'</option>');
                    }
                });
            }else {
                layer.msg("分管区域数据加载失败");
            }
        });
    },
    /**
     * 查询自营厅所有营业厅
     * @param $id
     */
    initAllDepart:function ($id) {
        var $obj = $("#"+$id);
        $obj.empty();
        var loading = layer.load(2);
        $.get(basePath+"tdMDepart/queryMyDepart",function (res) {
            layer.close(loading);
            if(res.success){
                var list=res.data;
                $obj.append(' <option value="-1">请选择营业厅</option>');
                list.forEach(function (t,i) {
                    if(i>100){
                        return;
                    }
                    $obj.append('<option value="'+t.DICTCODE+'">'+t.DICTVALUE+'</option>');
                })
            }else {
                layer.msg("营业厅数据加载失败");
            }
        });
    }
};