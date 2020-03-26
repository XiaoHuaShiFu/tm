package com.xiaohuashifu.tm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.xiaohuashifu.tm.dao.BookMapper;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.service.BookService;
import com.xiaohuashifu.tm.service.FileService;
import com.xiaohuashifu.tm.service.constant.BookConstant;
import com.xiaohuashifu.tm.result.Result;

@Service("bookService")
public class BookServiceImpl implements BookService {

	private final BookMapper bookMapper;
	private final FileService fileService;
	
	@Autowired
	public BookServiceImpl(BookMapper bookMapper, FileService fileService) {
		this.bookMapper = bookMapper;
		this.fileService = fileService;
	}

	@Override
	public Result save(BookDO book, MultipartFile cover) {
		if(cover != null) {
			String coverUrl = fileService.saveAndGetUrl(cover, BookConstant.PREFIX_COVER_FILE_DIRECTORY);
			book.setCoverUrl(coverUrl);
		}
		bookMapper.insert(book);
		return Result.success();
	}
	
	@Override
	public Result delete(Integer id) {
		String coverUrl = bookMapper.getCoverById(id);
		if(coverUrl != null) {
			fileService.delete(coverUrl);
		}
		bookMapper.delete(id);
		return Result.success();
	}

	@Override
	public List<BookDO> selectAll() {
		return null;
	}
	
}
