var numberUtilsObj = {
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