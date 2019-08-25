package cn.leyou.auth.service;

import cn.leyou.auth.client.UserClient;
import cn.leyou.auth.config.JwtProperties;
import cn.leyou.auth.pojo.UserInfo;
import cn.leyou.auth.utils.JwtUtils;
import cn.leyou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    public String authentication(String username, String password) {
        User user = this.userClient.queryUser(username, password);
        if (user == null){
            return null;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(username);
        try {
            String token = JwtUtils.generateToken(userInfo, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
