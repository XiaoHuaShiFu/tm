package com.xiaohuashifu.tm.controller.v1.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.BookService;

@RestController
@RequestMapping("v1/books")
@Validated
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
	public Boolean insertBook(@RequestPart("bookInfo") BookDO book,
							  @RequestPart(value = "cover", required = false) MultipartFile cover) {
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
	
//	@ResponseBody
//	@ResponseStatus(HttpStatus.OK)
//	@RequestMapping(method = RequestMethod.GET)
//	public Object getBooks(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
//		Result<PageInfo<BookDO>> result = bookService.listBooks(new BookQuery(pageNum));
//		if(result.isSuccess()) {
//			return result.getData();
//		}
//		return result.getMessage();
//	}
	
}
