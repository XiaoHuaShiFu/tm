package com.xiaohuashifu.tm.pojo.vo;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-04-01 23:13
 */
public class MeetingQrcodeVO {

    private Integer id;

    private String qrcode;

    public MeetingQrcodeVO() {
    }

    public MeetingQrcodeVO(Integer id, String qrcode) {
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
        return "MeetingQrcodeVO{" +
                "id=" + id +
                ", qrcode='" + qrcode + '\'' +
                '}';
    }
}
