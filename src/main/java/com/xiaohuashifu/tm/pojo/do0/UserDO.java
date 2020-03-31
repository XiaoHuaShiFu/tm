package com.xiaohuashifu.tm.pojo.do0;

import com.xiaohuashifu.tm.constant.Department;
import com.xiaohuashifu.tm.constant.Gender;
import com.xiaohuashifu.tm.pojo.do0.group.Group;
import com.xiaohuashifu.tm.pojo.do0.group.GroupPost;
import com.xiaohuashifu.tm.validator.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-03-25 18:37
 */
public class UserDO {

    @Id(groups = {Group.class})
    private Integer id;

    private String openid;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The jobNumber must be not blank.",
            groups = {GroupPost.class})
    @JobNumber(groups = {Group.class})
    private String jobNumber;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The password must be not blank.",
            groups = {GroupPost.class})
    @Password(groups = {Group.class})
    private String password;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The nickName must be not blank.",
            groups = {GroupPost.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of nickName must be between 1 to 20.",
            min = 1, max = 20,
            groups = {Group.class})
    private String nickName;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The fullName must be not blank.",
            groups = {GroupPost.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of fullName must be between 1 to 20.",
            min = 1, max = 20,
            groups = {Group.class})
    private String fullName;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The gender must be not blank.",
            groups = {GroupPost.class})
    private Gender gender;

    private Date birthday;

    @Phone(groups = {Group.class})
    private String phone;

    @Email(groups = {Group.class})
    private String email;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The department must be not blank.",
            groups = {GroupPost.class})
    private Department department;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The avatarUrl must be not blank.",
            groups = {GroupPost.class})
    @Url(groups = {Group.class})
    private String avatarUrl;

    private Integer point;

    private Boolean available;

    private Date createTime;

    private Date updateTime;

    public UserDO() {
    }

    public UserDO(Integer id, String openid, String jobNumber, String password, String nickName, String fullName, com.xiaohuashifu.tm.constant.Gender gender, Date birthday, String phone, String email, Department department, String avatarUrl, Integer point, Boolean available, Date createTime, Date updateTime) {
        this.id = id;
        this.openid = openid;
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
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public com.xiaohuashifu.tm.constant.Gender getGender() {
        return gender;
    }

    public void setGender(com.xiaohuashifu.tm.constant.Gender gender) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
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
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
