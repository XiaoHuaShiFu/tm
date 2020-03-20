package com.xiaohuashifu.tm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaohuashifu.tm.dao.BookMapper;
import com.xiaohuashifu.tm.pojo.do0.Book;
import com.xiaohuashifu.tm.service.BookService;

@Service("bookService")
public class BookServiceImpl implements BookService {

	@Autowired
	BookMapper bookMapper;
	
	@Override
	public void insert(Book book) {
		bookMapper.insert(book);
	}
	
}
