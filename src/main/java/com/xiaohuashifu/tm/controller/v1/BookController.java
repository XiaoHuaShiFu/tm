package com.xiaohuashifu.tm.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.BookService;

@Controller
@RequestMapping("v1/books")
public class BookController {

	private final BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@RequestMapping("")
	public String testbook() {
		return "admin/testbook";
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public String insertBook(@RequestPart("bookInfo") BookDO book, @RequestPart(value = "cover",required = false) MultipartFile cover) {
		Result result = bookService.save(book, cover);
		return result.isSuccess().toString();
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method = RequestMethod.DELETE)
	public String deleteBook(@RequestParam("id") Integer id) {
		Result result = bookService.delete(id);
		return result.isSuccess().toString();
	}
	
}
