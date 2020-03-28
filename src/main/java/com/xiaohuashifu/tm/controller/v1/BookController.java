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

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
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
	
	//这里先写测试，等功能完全后用下面的getBooks代替这个方法
	@RequestMapping(method = RequestMethod.GET)
	public String testbook() {
		return "admin/testbook";
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public Boolean insertBook(@RequestPart("bookInfo") BookDO book, @RequestPart(value = "cover",required = false) MultipartFile cover) {
		Result result = bookService.saveBook(book, cover);
		return result.isSuccess();
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method = RequestMethod.DELETE)
	public Boolean deleteBook(@RequestParam("id") Integer id) {
		Result result = bookService.deleteBook(id);
		return result.isSuccess();
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Boolean updateBook(BookDO book) {
		Result result = bookService.updateBook(book);
		return result.isSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value = "cover", method = RequestMethod.PUT)
	public String updateCover(@RequestParam("id") Integer id, @RequestParam("cover") MultipartFile cover) {
		Result result = bookService.updateCover(id, cover);
		return result.isSuccess().toString();
	}
	
/*	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	public PageInfo<BookDO> getBooks(@RequestParam("pageNum") Integer page) {
		Result<PageInfo<BookDO>> result = bookService.listBooks(new BookQuery(page));
		return result.getData();
	}*/
	
}
