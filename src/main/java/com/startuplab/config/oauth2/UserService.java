package com.startuplab.config.oauth2;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.startuplab.service.CommonService;
import com.startuplab.vo.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    private CommonService common;

    @PostConstruct
    public void init() {
        try {
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = common.getUserByEmail(email);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        MyUser myuser = new MyUser(user);
        return myuser;
    }
}
