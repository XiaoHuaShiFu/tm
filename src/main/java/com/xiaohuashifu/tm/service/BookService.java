package com.xiaohuashifu.tm.service;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
import com.xiaohuashifu.tm.result.Result;

public interface BookService {
	Result save(BookDO book, MultipartFile cover);
	Result delete(Integer id);
	Result<PageInfo<BookDO>> listBooks(BookQuery bookQuery);
}
