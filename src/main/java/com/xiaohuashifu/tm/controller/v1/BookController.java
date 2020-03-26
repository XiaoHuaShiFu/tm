package com.xiaohuashifu.tm.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.service.BookService;

@Controller
public class BookController {

	private final BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@RequestMapping("/testbook")
	public String testbook() {
		return "admin/testbook";
	}
	
	@ResponseBody
	@RequestMapping(value = "book", method = RequestMethod.POST)
	public String insertBook(@RequestPart("bookInfo") BookDO book, @RequestPart(value = "cover",required = false) MultipartFile cover) {
		bookService.insert(book, cover);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping(value = "books", method = RequestMethod.DELETE)
	public String deleteBook(@RequestParam("id") String id) {
		Integer bookId = Integer.parseInt(id);
		bookService.delete(bookId);
		return "ok";
	}
	
}
