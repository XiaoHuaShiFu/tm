package com.xiaohuashifu.tm.constant;

/**
 * 描述: 部门集合
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 2:44
 */
public enum Department {

    ALL("全体"), UNKNOWN("未知"), ZZB("制造部"), XSB("销售部"), RSB("人事部"), YFB("研发部"),
    CWB("财务部"), CGB("采购部"), PZB("品质部"), GZB("管制部"), KFB("客服部");

    /**
     * 部门的中文
     */
    private final String cn;

    Department(String cn) {
        this.cn = cn;
    }

    public String getCn() {
        return cn;
    }

}
