package cn.leyou.user.service;

import cn.leyou.common.util.NumberUtils;
import cn.leyou.user.mapper.UserMapper;
import cn.leyou.user.pojo.User;
import cn.leyou.user.utils.CodecUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.sound.midi.Soundbank;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    static final String KEY_PREFIX = "user:code:phone:";

    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 检查数据
     * @param data
     * @param type
     * @return
     */
    public Boolean checkData(String data, Integer type) {
        User record = new User();
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                return null;
        }
        return this.userMapper.selectCount(record) == 0;
    }

    /**
     * 阿里云短信验证码
     * @param phone
     * @return
     */
    public Boolean sendVerifyCode(String phone) {
        if (StringUtils.isEmpty(phone)){
            return false;
        }
        // 生成验证码
        String code = NumberUtils.generateCode(6);
        try {
            // 发送短信
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            this.amqpTemplate.convertAndSend("leyou.sms.exchange", "sms.verify.code", msg);
            // 将code存入redis
            this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 90, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            logger.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }
    }

    /**
     * 注册
     * @param user
     * @param code
     */
    @Transactional
    public Boolean register(User user, String code) {
        //从缓存中获取验证码
        String cacheCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        if (!org.apache.commons.lang3.StringUtils.equals(code, cacheCode)) {
            return false;
        }
        //获取盐值
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        //密码加盐加密
        System.out.println(user.getPassword());
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        System.out.println(user.getPassword());

        //新增用户
        user.setId(null);
        user.setCreated(new Date());

        return this.userMapper.insert(user) >= 1;
    }

    /**
     * 登录查询
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password) {
        // 根据用户名去数据库中查找用户
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        if (user == null){
            return null;
        }
        // 获取盐，对用户输入的密码进行加盐加密
        password = CodecUtils.md5Hex(password, user.getSalt());
        // 和数据库中的密码进行比较
        if (org.apache.commons.lang3.StringUtils.equals(password, user.getPassword())){
            return user;
        }
        return null;
    }
}