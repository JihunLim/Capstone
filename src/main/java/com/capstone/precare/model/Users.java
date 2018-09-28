package com.capstone.precare.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자 Model (DTO, VO)
 *
 * created 18.08.28  by 임지훈
 */

@Getter
@Setter
@ToString
public class Users {
    private String user_id;
    private String user_pwd;
    private int user_enabled;
    private String user_role;
    private String user_email;
    private String user_birth;
    private String user_name;
    private String user_phone;

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_role() {
        return user_role;
    }

    public Users() {
    }

    public Users(String user_id, String user_pwd, int user_enabled, String user_role, String user_email, String user_birth, String user_name, String user_phone) {
        this.user_id = user_id;
        this.user_pwd = user_pwd;
        this.user_enabled = user_enabled;
        this.user_role = user_role;
        this.user_email = user_email;
        this.user_birth = user_birth;
        this.user_name = user_name;
        this.user_phone = user_phone;
    }
}
