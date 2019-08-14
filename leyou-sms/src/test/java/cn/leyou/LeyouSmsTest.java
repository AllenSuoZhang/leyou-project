package cn.leyou;

import cn.leyou.sms.config.SmsProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties(SmsProperties.class)
public class LeyouSmsTest {
    @Autowired
    private SmsProperties prop;

    @Test
    public void test(){
        System.out.println(prop.getAccessKeyId());
        System.out.println(prop.getAccessKeySecret());
        System.out.println(prop.getSignName());
        System.out.println(prop.getVerifyCodeTemplate());
    }
}
