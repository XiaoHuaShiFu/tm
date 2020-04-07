package com.xiaohuashifu.tm.controller.v1.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.BookService;
import com.xiaohuashifu.tm.service.TokenService;

@RestController
@RequestMapping("v1/books")
@Validated
public class BookController {

	private final BookService bookService;
	private final TokenService tokenService;
	
	@Autowired
	public BookController(BookService bookService, TokenService tokenService) {
		this.bookService = bookService;
		this.tokenService = tokenService;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public Boolean insertBook(@RequestPart("bookInfo") BookDO book,
							  @RequestPart(value = "cover", required = false) MultipartFile cover) {
		Result<?> result = bookService.saveBook(book, cover);
		return result.isSuccess();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method = RequestMethod.DELETE)
	public Boolean deleteBook(@RequestParam("id") Integer id) {
		Result<?> result = bookService.deleteBook(id);
		return result.isSuccess();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.PUT)
	public Boolean updateBook(BookDO book) {
		Result<?> result = bookService.updateBook(book);
		return result.isSuccess();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "cover", method = RequestMethod.PUT)
	public String updateCover(@RequestParam("id") Integer id, @RequestParam("cover") MultipartFile cover) {
		Result<?> result = bookService.updateCover(id, cover);
		return result.isSuccess().toString();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{pageNum}", method = RequestMethod.GET)
	public void getBooks(HttpServletRequest request, HttpServletResponse response, @PathVariable("pageNum") Integer pageNum) {
		BookQuery bookQuery = new BookQuery(pageNum);
		Result<PageInfo<BookDO>> result = bookService.listBooks(bookQuery);
		if (result.isSuccess()) {
			PageInfo<BookDO> booksInfo = result.getData();
			List<BookDO> books = booksInfo.getList();
			request.setAttribute("books", books);
			request.setAttribute("total", booksInfo.getTotal());
			request.setAttribute("pageSize", bookQuery.getPageSize());
			request.setAttribute("pageNum", pageNum);
			//这里先注释，等测试结束开放token时再删除注释
//			TokenType type = null;  //当前使用者身份
//			String token = request.getHeader("authorization");
//			Result<TokenAO> tokenResult = tokenService.getToken(token);
//			if (result.isSuccess()) {
//				type = tokenResult.getData().getType();
//			}else {
//				response.setStatus(HttpStatus.BAD_REQUEST.value());
//			}
			try {
				//这里先注释，等测试结束开放token时再删除注释
//				if (TokenType.USER.equals(type)) {
//					request.getRequestDispatcher("/v1/user/books").forward(request, response);
//				}else {
//					request.getRequestDispatcher("/v1/admin/books").forward(request, response);					
//				}
				request.getRequestDispatcher("/v1/admin/books").forward(request, response);   //用于测试admin
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}
	
}
