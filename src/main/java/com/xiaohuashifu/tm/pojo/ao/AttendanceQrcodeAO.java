package com.xiaohuashifu.tm.pojo.ao;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-01 23:13
 */
public class AttendanceQrcodeAO {

    private String qrcode;

    public AttendanceQrcodeAO() {
    }

    public AttendanceQrcodeAO(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Override
    public String toString() {
        return "AttendanceQrcodeAO{" +
                "qrcode='" + qrcode + '\'' +
                '}';
    }
}
