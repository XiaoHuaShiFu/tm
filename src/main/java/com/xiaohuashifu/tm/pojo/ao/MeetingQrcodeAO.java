package com.xiaohuashifu.tm.pojo.ao;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-01 23:13
 */
public class MeetingQrcodeAO {

    private Integer id;

    private String qrcode;

    public MeetingQrcodeAO() {
    }

    public MeetingQrcodeAO(Integer id, String qrcode) {
        this.id = id;
        this.qrcode = qrcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Override
    public String toString() {
        return "MeetingQrcodeAO{" +
                "id=" + id +
                ", qrcode='" + qrcode + '\'' +
                '}';
    }
}
