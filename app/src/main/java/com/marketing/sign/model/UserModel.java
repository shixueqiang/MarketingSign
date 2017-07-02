package com.marketing.sign.model;

import com.marketing.sign.db.annotation.Column;
import com.marketing.sign.db.annotation.Table;

/**
 * Created by shixq on 2017/7/2.
 */
@Table(name = UserModel.TABLE_NAME)
public class UserModel extends BaseModel {
    public static final String TABLE_NAME = "user";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String STATUS = "status";
    public static final String LOGIN_TIME = "login_time";
    public static final String START_SIGN_TIME = "start_sign_time";
    public static final String END_SIGN_TIME = "end_sign_time";
    @Column(name = ACCOUNT)
    private String account;
    @Column(name = PASSWORD)
    private String password;
    @Column(name = NAME)
    private String name;
    @Column(name = PHONE)
    private String phone;
    @Column(name = STATUS)
    //签到状态 0 未签到 1已签到 2完成签到
    private int status;
    @Column(name = LOGIN_TIME)
    private String loginTime;
    @Column(name = START_SIGN_TIME)
    private String startSignTime;
    @Column(name = END_SIGN_TIME)
    private String endSignTime;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public String getStartSignTime() {
        return startSignTime;
    }

    public String getEndSignTime() {
        return endSignTime;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public void setStartSignTime(String startSignTime) {
        this.startSignTime = startSignTime;
    }

    public void setEndSignTime(String endSignTime) {
        this.endSignTime = endSignTime;
    }
}
