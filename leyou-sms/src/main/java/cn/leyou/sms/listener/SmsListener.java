package cn.leyou.sms.listener;

import cn.leyou.sms.utils.SmsUtils;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonResponse;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.sms.queue", durable = "true"),
            exchange = @Exchange(value = "leyou.sms.exchange",
                    ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"}))
    public void sendSms(JSONObject jsonObject) throws Exception{
        if (jsonObject.isEmpty()){
            return;
        }
        String phone = jsonObject.getString("phone");
        String code = jsonObject.getString("code");

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            // 放弃处理
            return;
        }

        CommonResponse response = this.smsUtils.sendSms(phone, code);
    }
}
