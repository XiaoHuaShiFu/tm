package com.xiaohuashifu.tm.timer.task;

import java.awt.print.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.xiaohuashifu.tm.constant.BookState;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.service.BookService;

/**
 * 删除过期的key的任务回调
 * 
 * @author TAO
 * @date 2020年4月27日 下午10:22:12
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

	private final BookService bookService;
	
	@Autowired
	public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer, BookService bookService) {
		super(listenerContainer);
		this.bookService = bookService;
	}
	
	/**
	 * 处理过期的key
	 * 
	 * @param message 过期的key
	 */
	@Override
	public void onMessage(Message message, byte[] pattern) {
		super.onMessage(message, pattern);
		String expiredKey = message.toString();
		if (expiredKey.startsWith("bookId")) {  //处理book的预定信息。key格式为: bookId:1 ; value为userId
			BookDO book = new BookDO();
			book.setId(Integer.parseInt(expiredKey.substring(7)));
			book.setState(BookState.IDLE);
			bookService.updateBook(book);
		}
	}
	
}
