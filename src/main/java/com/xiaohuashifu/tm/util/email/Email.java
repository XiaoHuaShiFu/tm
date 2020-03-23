package com.xiaohuashifu.tm.util.email;

import java.util.List;

public class Email {
    private List<String> to; // 收件人

    private String from; // 发件人

    private String host; // SMTP主机

    private String username; // 发件人的用户名

    private String password; // 发件人的密码

    private String subject; // 邮件主题

    private String content; // 邮件正文

    private List<String> file; // 多个附件

    public Email() {
    }

    public Email(List<String> to, String from, String host, String username, String password, String subject, String content, List<String> file) {
        this.to = to;
        this.from = from;
        this.host = host;
        this.username = username;
        this.password = password;
        this.subject = subject;
        this.content = content;
        this.file = file;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Email{" +
                "to=" + to +
                ", from='" + from + '\'' +
                ", host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", file=" + file +
                '}';
    }
}