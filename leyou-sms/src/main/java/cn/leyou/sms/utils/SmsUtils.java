package cn.leyou.sms.utils;

import cn.leyou.sms.config.SmsProperties;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.rabbitmq.tools.json.JSONUtil;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtils {

    @Autowired
    private SmsProperties prop;

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    static final Logger logger = LoggerFactory.getLogger(SmsUtils.class);

    public CommonResponse sendSms(String phoneNumbers, String code) throws Exception {
        return sendSms(phoneNumbers, code, prop.getSignName(), prop.getVerifyCodeTemplate());
    }

    public CommonResponse sendSms(String phoneNumbers, String code, String signName, String templateCode) throws Exception {
        DefaultProfile profile = DefaultProfile.getProfile("default", prop.getAccessKeyId(), prop.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        JSONObject templateParam = new JSONObject();
        templateParam.put("code", code);

        //组装请求对象-具体描述见控制台-文档部分内容
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(domain);
        request.setSysProduct(product);
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //必填:待发送手机号
        request.putBodyParameter("PhoneNumbers", phoneNumbers);
        //必填:短信签名-可在短信控制台中找到
        request.putBodyParameter("SignName", signName);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.putBodyParameter("TemplateParam", templateParam.toJSONString());
        //必填:短信模板-可在短信控制台中找到
        request.putBodyParameter("TemplateCode", templateCode);

        CommonResponse response = client.getCommonResponse(request);

        logger.info("发送短信状态：{}", response.getHttpStatus());
        logger.info("发送短信消息：{}", response.getData());

        return response;
    }
}