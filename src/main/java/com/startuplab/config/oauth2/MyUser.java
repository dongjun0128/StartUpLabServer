package com.startuplab.config.oauth2;

import java.util.Arrays;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import lombok.Getter;

@Getter
public class MyUser extends User {

    private com.startuplab.vo.User account;

    public MyUser(com.startuplab.vo.User user) {
        super(user.getUser_email(), user.getUser_password(), Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
        this.account = user;
    }
}
