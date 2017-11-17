var dataDictUtils = {
    getListByType:function (q_type,callback) {
        var list = [];
        var loading = layer.load(2);
        $.post(basePath+"sysDataDict/querySysDataDictByDictType",{type:q_type},function (res) {
            layer.close(loading);
            if(res.code==='error'){
                layer.msg(res.message);
                return;
            }

            if(null!=res.data){
                res.data.forEach(function (t) {
                    if(''!=t.parentId && null!=t.parentId){
                        list.push(t);
                    }

                })
            }
            if(callback){
                callback(list);
            }
        });
        return list;
    },
    /**
     *
     * @param q_type  数据字典标识
     * @param $id     dom节点id 不带#
     * @returns {Array}
     */
    getListAndInitDropDown:function (q_type,$id) {
        var list = [];
        var loading = layer.load(2);
        $.post(basePath+"sysDataDict/querySysDataDictByDictType",{type:q_type},function (res) {
            layer.close(loading);
            if(res.code==='error'){
                layer.msg(res.message);
                return;
            }

            if(null!=res.data){
                res.data.forEach(function (t) {
                    if(''!=t.parentId && null!=t.parentId){
                        list.push(t);
                    }

                })
            }
            dataDictUtils.initDropDown($id,list);
        });
        return list;
    },
    initDropDown:function ($id,listAry) {
        var $obj=$("#"+$id);
        $obj.empty();
        listAry.forEach(function (t) {
            if(t.dictCode==="-1"){
                $obj.append(' <option selected value="'+t.dictCode+'">'+t.dictValue+'</option>')
            }else{
                $obj.append(' <option value="'+t.dictCode+'">'+t.dictValue+'</option>')
            }
        })
    },
    initDepartDropDown:function ($id) {
        var $obj=$("#"+$id);
        $obj.empty();
        $obj.append(' <option value="-1">全部</option>');
        //1. 获取区域信息
        $.get(basePath+"tdMDepart/queryMySubCompany",function (res) {
            if(res.success){
                var list=res.data;
                $obj.append('<optgroup label="区域">');
                list.forEach(function (t,i) {
                    if(i!=0){
                        $obj.append(' <option value="'+t.DICTCODE+'">'+t.DICTVALUE+'</option>');
                    }
                });
                $obj.append('</optgroup>');
            }else {
                layer.msg("分管区域数据加载失败");
            }
        });
        //2. 加载营业厅信息
        $.get(basePath+"tdMDepart/queryMyDepart",function (res) {
            if(res.success){
                var list=res.data;
                $obj.append('<optgroup label="营业厅">');
                list.forEach(function (t,i) {
                    if(i!=0){
                        $obj.append(' <option value="'+t.DICTCODE+'">'+t.DICTVALUE+'</option>');
                    }
                })
                $obj.append('</optgroup>');
            }else {
                layer.msg("营业厅数据加载失败");
            }
        });
    }
};