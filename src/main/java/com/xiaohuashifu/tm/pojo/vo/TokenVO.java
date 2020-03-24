package com.xiaohuashifu.tm.pojo.vo;

/**
 * 描述: Token对象
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 11:23
 */
public class TokenVO {

    private Integer id;

    private String type;

    private String token;

    public TokenVO() {
    }

    public TokenVO(Integer id, String type, String token) {
        this.id = id;
        this.type = type;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenVO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
