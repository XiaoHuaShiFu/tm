package com.xiaohuashifu.tm.manager;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.pojo.vo.BookLogVO;
import com.xiaohuashifu.tm.result.Result;

public interface BookLogManager {
	
	Result<PageInfo<BookLogVO>> listBookLogs(BookLogQuery bookLogQuery);
	
}
