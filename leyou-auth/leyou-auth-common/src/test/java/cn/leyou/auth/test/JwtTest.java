package cn.leyou.auth.test;

import cn.leyou.auth.pojo.UserInfo;
import cn.leyou.auth.utils.JwtUtils;
import cn.leyou.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "C:\\IDEAProject\\rsa\\rsa.pub";

    private static final String priKeyPath = "C:\\IDEAProject\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU2NjIzMDc3Mn0.MHebX5OYLpjxIpAGhmApdfQFquUqb1jQzsZfvXZMvd3RyYUtVec2CXw99W1RS8vSQqZ-SMvx_Zi4bmt_avKsHYsPQpKIcznW0SsK4E2We-r5GlHPdr7Zpw4WpUnjqEuMEojLZDUkDKofrDOkiBYrzwlVVqMBanejv6AYtRFWrKA";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}