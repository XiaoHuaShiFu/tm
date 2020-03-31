package com.xiaohuashifu.tm.pojo.query;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 16:38
 */
public class UserQuery {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    // TODO: 2020/4/1 还未写完 
    private Integer id;
    private String jobNumber;
    private String nickName;
    private String fullName;
    private String gender;
//            this.id = id;
//        this.openid = openid;
//        this.jobNumber = jobNumber;
//        this.password = password;
//        this.nickName = nickName;
//        this.fullName = fullName;
//        this.gender = gender;
//        this.birthday = birthday;
//        this.phone = phone;
//        this.email = email;
//        this.department = department;
//        this.avatarUrl = avatarUrl;
//        this.point = point;
//        this.available = available;

    public UserQuery() {
    }

    public UserQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "UserQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
