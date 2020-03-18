package com.xiaohuashifu.tm.dao;

import com.xiaohuashifu.tm.pojo.do0.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-18 14:09
 */
@Mapper
public interface TestMapper {

    int insert(Test test);

    List<Test> selectAll();
}
