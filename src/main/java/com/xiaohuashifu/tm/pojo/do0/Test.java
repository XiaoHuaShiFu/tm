package com.xiaohuashifu.tm.pojo.do0;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-18 14:08
 */
public class Test {

    private Integer id;
    private String name;

    public Test() {
    }

    public Test(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
