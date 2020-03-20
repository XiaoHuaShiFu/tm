package com.xiaohuashifu.tm.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.xiaohuashifu.tm.pojo.do0.Book;

@Mapper
public interface BookMapper {
	void insert(@Param("book") Book book);
}
