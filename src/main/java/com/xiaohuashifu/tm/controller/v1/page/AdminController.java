package com.xiaohuashifu.tm.controller.v1.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.aspect.annotation.AdminLog;
import com.xiaohuashifu.tm.aspect.annotation.TokenAuth;
import com.xiaohuashifu.tm.constant.AdminLogType;
import com.xiaohuashifu.tm.constant.Department;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.manager.AdminLogManager;
import com.xiaohuashifu.tm.manager.AttendanceManager;
import com.xiaohuashifu.tm.manager.BookLogManager;
import com.xiaohuashifu.tm.manager.MeetingManager;
import com.xiaohuashifu.tm.manager.MeetingParticipantManager;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.do0.BookDO;
import com.xiaohuashifu.tm.pojo.do0.BookLogDO;
import com.xiaohuashifu.tm.pojo.do0.MeetingDO;
import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.pojo.query.AdminLogQuery;
import com.xiaohuashifu.tm.pojo.query.AttendanceQuery;
import com.xiaohuashifu.tm.pojo.query.BookLogQuery;
import com.xiaohuashifu.tm.pojo.query.BookQuery;
import com.xiaohuashifu.tm.pojo.query.MeetingParticipantQuery;
import com.xiaohuashifu.tm.pojo.query.MeetingQuery;
import com.xiaohuashifu.tm.pojo.query.UserQuery;
import com.xiaohuashifu.tm.pojo.vo.AdminLogVO;
import com.xiaohuashifu.tm.pojo.vo.AttendanceVO;
import com.xiaohuashifu.tm.pojo.vo.BookLogVO;
import com.xiaohuashifu.tm.pojo.vo.MeetingParticipantVO;
import com.xiaohuashifu.tm.pojo.vo.MeetingVO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.AdminService;
import com.xiaohuashifu.tm.service.BookService;
import com.xiaohuashifu.tm.service.MeetingService;
import com.xiaohuashifu.tm.service.TokenService;
import com.xiaohuashifu.tm.service.UserService;

@Controller
@RequestMapping("v1/admin")
public class AdminController {

	private final AdminService adminService;
	private final UserService userService;
	private final BookService bookService;
	private final BookLogManager bookLogManager;
	private final MeetingService meetingService;
	private final MeetingManager meetingManager;
	private final MeetingParticipantManager meetingParticipantManager;
	private final AttendanceManager attendanceManager;
	private final AdminLogManager adminLogManager;
	private final TokenService tokenService;
	
	@Autowired
	public AdminController(AdminService adminService, UserService userService,
			BookService bookService, BookLogManager bookLogManager,
			MeetingService meetingService, MeetingManager meetingManager,
			MeetingParticipantManager meetingParticipantManager,
			AttendanceManager attendanceManager, AdminLogManager adminLogManager,
			TokenService tokenService) {
		this.adminService = adminService;
		this.userService = userService;
		this.bookService = bookService;
		this.bookLogManager = bookLogManager;
		this.meetingService = meetingService;
		this.meetingManager = meetingManager;
		this.meetingParticipantManager = meetingParticipantManager;
		this.attendanceManager = attendanceManager;
		this.adminLogManager = adminLogManager;
		this.tokenService = tokenService;
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "admin/login";
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public void adminLogin(HttpServletRequest request, HttpServletResponse response, 
					@RequestParam("jobNumber") String jobNumber, @RequestParam("password") String password) {
		Result<TokenAO> result = tokenService.createAndSaveToken(TokenType.ADMIN, jobNumber, password);
		if(result.isSuccess()) {
			request.getSession().setAttribute("token", result.getData().getToken());
			try {
				response.sendRedirect(request.getScheme() + "://" +
						request.getServerName() + ":"+
						request.getServerPort() + "/v1/admin/index");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
//	@TokenAuth(tokenType = {TokenType.ADMIN})
//	@AdminLog(value = "登录", type = AdminLogType.LOGIN)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("admin/index");
		model.addObject("token", request.getSession().getAttribute("token"));
		request.getSession().removeAttribute("token");
		Result<String> result = adminService.getAnnouncement();
		if (result.isSuccess()) {
			model.addObject("announcement", result.getData());
		} else {
			model.setViewName("admin/error");
			model.addObject("error_msg", "服务器获取公告时发生错误");
		}
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "announcement", method = RequestMethod.PUT)
	public String updateAnnouncement(@RequestParam("announcement") String announcement) {
		Result<?> result = adminService.updateAnnouncement(announcement);
		if (result.isSuccess()) {
			return "ok";
		}
		return "error";
	}

	@RequestMapping(value = "books/{pageNum}", method = RequestMethod.GET)
	public ModelAndView getBooks(@PathVariable("pageNum") Integer pageNum,
			@RequestParam(value = "name", required = false) String name) {
		ModelAndView model = new ModelAndView("admin/books");
		BookQuery bookQuery = new BookQuery(pageNum);
		if (name != null) {
			bookQuery.setName(name.trim());
		}
		Result<PageInfo<BookDO>> result = bookService.listBooks(bookQuery);
		if (result.isSuccess()) {
			PageInfo<BookDO> booksInfo = result.getData();
			List<BookDO> books = booksInfo.getList();
			model.addObject("books", books);
			model.addObject("total", booksInfo.getTotal());
			model.addObject("pageSize", bookQuery.getPageSize());
			model.addObject("pageIndex", pageNum);
		} else {
			model.addObject("error", "error");
		}
		return model;
	}
	
	@RequestMapping(value = "bookLogs/{pageNum}", method = RequestMethod.GET)
	public ModelAndView getBookLogs(@PathVariable("pageNum") Integer pageNum,
			@RequestParam(value = "username", required = false) String username) {
		ModelAndView model = new ModelAndView("admin/bookLogs");
		BookLogQuery bookLogQuery = new BookLogQuery(pageNum);
		Result<PageInfo<BookLogVO>> result = bookLogManager.listBookLogs(bookLogQuery);
		if (result.isSuccess()) {
			PageInfo<BookLogVO> bookLogsInfo = result.getData();
			List<BookLogVO> bookLogs = bookLogsInfo.getList();
			model.addObject("bookLogs", bookLogs);
			model.addObject("total", bookLogsInfo.getTotal());
			model.addObject("pageSize", bookLogQuery.getPageSize());
			model.addObject("pageIndex", pageNum);
		}else {
			model.addObject("error", "error");
		}
		return model;
	}

	@RequestMapping(value = "members/{pageNum}", method = RequestMethod.GET)
	public ModelAndView members(@PathVariable("pageNum") Integer pageNum,
			@RequestParam(value = "jobNumber", required = false) String jobNumber,
			@RequestParam(value = "department", required = false) Department department,
			@RequestParam(value = "sort", required = false, defaultValue = "false") Boolean sort) {
		ModelAndView model = new ModelAndView("admin/members");
		UserQuery userQuery = new UserQuery(pageNum);
		Result<?> result = null;
		if (jobNumber != null) {
			result = userService.getUserByJobNumber(jobNumber.trim());
		} else {
			if (department != null) {
				userQuery.setDepartment(department);
			}
			if (sort == true) {
				userQuery.setOrderByPoint(true);
			}
			result = userService.listUsers(userQuery);
		}
		if (result.isSuccess()) {
			Object data = result.getData();
			List<UserDO> users = null;
			if (data instanceof UserDO) {  //根据工号得到的结果
				users = new ArrayList<>();
				users.add((UserDO)data);
			}else {
				PageInfo<UserDO> usersInfo = (PageInfo<UserDO>)result.getData();
				users = usersInfo.getList();
				model.addObject("total", usersInfo.getTotal());
				model.addObject("pageSize", userQuery.getPageSize());
				model.addObject("pageIndex", pageNum);
				model.addObject("department", department);
				model.addObject("sort", sort);
			}
			model.addObject("users", users);
		} else {
			model.addObject("error", "error");
		}
		return model;
	}
	
	@RequestMapping(value = "meetings/{pageNum}", method = RequestMethod.GET)
	public ModelAndView meetings(@PathVariable("pageNum") Integer pageNum, 
			@RequestParam(value = "name", required = false) String name) {
		ModelAndView model = new ModelAndView("admin/meetings");
		Result result = null;
		MeetingQuery meetingQuery = new MeetingQuery(pageNum);
		if (name != null) {
//			result = meetingService.getMeeting(id);
		}else {
			result = meetingManager.listMeetings(meetingQuery);
		}
		if (result.isSuccess()) {
			PageInfo<MeetingVO> meetingsInfo = (PageInfo<MeetingVO>) result.getData();
			List<MeetingVO> meetings = meetingsInfo.getList();
			for (MeetingVO meeting : meetings) {
				if (meeting.getContent().length() > 5) {
					meeting.setContent(new StringBuilder(meeting.getContent().substring(0, 5)).append("...").toString());
				}
			}
			model.addObject("meetings", meetings);
			model.addObject("total", meetingsInfo.getTotal());
			model.addObject("pageSize", meetingQuery.getPageSize());
			model.addObject("pageIndex", pageNum);
		}else {
			model.addObject("error", "error");
		}
		return model;
	}
	
	@RequestMapping(value = "meetings/{meetingId}/participants/{pageNum}", method = RequestMethod.GET)
	public ModelAndView meetingParticipants(@PathVariable("meetingId") Integer meetingId, @PathVariable("pageNum") Integer pageNum,
			@RequestParam("prevPageNum") Integer prevPageNum) {
		ModelAndView model = new ModelAndView("admin/meetingParticipants");
		MeetingParticipantQuery query = new MeetingParticipantQuery(pageNum, meetingId);
		Result<PageInfo<MeetingParticipantVO>> participantResult = meetingParticipantManager.listMeetingParticipants(query);
		Result<MeetingDO> meetingResult = meetingService.getMeeting(meetingId);
		if (participantResult.isSuccess() && meetingResult.isSuccess()) {
			PageInfo<MeetingParticipantVO> participantsInfo = participantResult.getData();
			List<MeetingParticipantVO> participants = participantsInfo.getList();
			model.addObject("meetingParticipants", participants);
			model.addObject("total", participantsInfo.getTotal());
			model.addObject("pageSize", query.getPageSize());
			model.addObject("pageIndex", pageNum);
			model.addObject("prevPageNum", prevPageNum);
			model.addObject("meeting", meetingResult.getData());
		}else {
			model.addObject("error", "error");
		}
		return model;
	}
	
	@RequestMapping(value = "attendances/{pageNum}", method = RequestMethod.GET)
	public ModelAndView attendances(@PathVariable("pageNum") Integer pageNum,
			@RequestParam(value = "department", required = false) Department department,
			@RequestParam(value = "month", required = false) Integer month) {
		ModelAndView model = new ModelAndView("admin/attendances");
		AttendanceQuery attendanceQuery = new AttendanceQuery(pageNum);
		if (department != null) {
			attendanceQuery.setDepartment(department);
		}
		if (month != null) {
			attendanceQuery.setMonth(month);
		}
		Result<PageInfo<AttendanceVO>> result = attendanceManager.listAttendances(attendanceQuery);
		if (result.isSuccess()) {
			PageInfo<AttendanceVO> attendancesInfo = result.getData();
			List<AttendanceVO> attendances = attendancesInfo.getList();
			model.addObject("attendances", attendances);
			model.addObject("total", attendancesInfo.getTotal());
			model.addObject("pageSize", attendanceQuery.getPageSize());
			model.addObject("pageIndex", pageNum);
			model.addObject("department", department);
			model.addObject("month", month);
		}else {
			model.addObject("error", "error");
		}
		return model;
	}
	
	@RequestMapping(value = "adminLogs/{pageNum}", method = RequestMethod.GET)
	public ModelAndView adminLogs(@PathVariable("pageNum") Integer pageNum) {
		ModelAndView model = new ModelAndView("admin/adminLogs");
		AdminLogQuery adminLogQuery = new AdminLogQuery(pageNum);
		Result<PageInfo<AdminLogVO>> result = adminLogManager.listAdminLogs(adminLogQuery);
		if (result.isSuccess()) {
			PageInfo<AdminLogVO> adminLogsInfo = result.getData();
			List<AdminLogVO> adminLogs = adminLogsInfo.getList();
			model.addObject("adminLogs", adminLogs);
			model.addObject("total", adminLogsInfo.getTotal());
			model.addObject("pageSize", adminLogQuery.getPageSize());
			model.addObject("pageIndex", pageNum);
		} else {
			model.addObject("error", "error");
		}
		return model;
	}
	
}
