package com.xiaohuashifu.tm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.result.Result;

public interface BookService {
	Result<BookDO> insert(BookDO book, MultipartFile cover);
	void delete(Integer id);
	List<BookDO> selectAll();
}
