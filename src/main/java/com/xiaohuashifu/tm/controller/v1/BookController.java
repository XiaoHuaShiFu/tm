package com.xiaohuashifu.tm.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaohuashifu.tm.pojo.do0.Book;
import com.xiaohuashifu.tm.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	@RequestMapping("/book")
	public String testbook() {
		return "admin/testbook";
	}
	
	@ResponseBody
	@RequestMapping("insertbook")
	public String insertBook(Book book) {
		book.setId(UUID.randomUUID().toString().substring(0, 8));
		System.out.println(book.toString());
		bookService.insert(book);
		return "ok";
	}
	
}
