package com.xiaohuashifu.tm.pojo.vo;

import com.xiaohuashifu.tm.constant.Department;
import com.xiaohuashifu.tm.constant.Gender;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-24 0:41
 */
public class UserVO {
    private Integer id;

    private String jobNumber;

    private String password;

    private String nickName;

    private String fullName;

    private Gender gender;

    private Date birthday;

    private String phone;

    private String email;

    private Department department;

    private String avatarUrl;

    private Integer point;

    private Boolean available;

    public UserVO() {
    }

    public UserVO(Integer id, String jobNumber, String password, String nickName, String fullName, Gender gender, Date birthday, String phone, String email, Department department, String avatarUrl, Integer point, Boolean available) {
        this.id = id;
        this.jobNumber = jobNumber;
        this.password = password;
        this.nickName = nickName;
        this.fullName = fullName;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.department = department;
        this.avatarUrl = avatarUrl;
        this.point = point;
        this.available = available;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", jobNumber='" + jobNumber + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", point=" + point +
                ", available=" + available +
                '}';
    }
}
