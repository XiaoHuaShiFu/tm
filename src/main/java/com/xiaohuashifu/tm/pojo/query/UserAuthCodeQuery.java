package com.xiaohuashifu.tm.pojo.query;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 16:38
 */
public class UserAuthCodeQuery {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer id;

    private List<Integer> idList;

    private Boolean used;

    public UserAuthCodeQuery() {
    }

    public UserAuthCodeQuery(Integer pageNum, Integer pageSize, Integer id, List<Integer> idList, Boolean used) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.id = id;
        this.idList = idList;
        this.used = used;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "UserAuthCodeQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", idList=" + idList +
                ", used=" + used +
                '}';
    }
}
