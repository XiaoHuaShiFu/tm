package com.xiaohuashifu.tm.pojo.ao;

/**
 * 描述: Token对象
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 11:23
 */
public class TokenAO {

    private String token;
    private String type;
    private Integer id;
    private String dep;

    public TokenAO() {
    }

    public TokenAO(String token, String type, Integer id, String dep) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.dep = dep;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    @Override
    public String toString() {
        return "TokenAO{" +
                "token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", dep='" + dep + '\'' +
                '}';
    }
}
