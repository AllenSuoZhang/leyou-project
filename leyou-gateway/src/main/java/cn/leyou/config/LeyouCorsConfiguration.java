package cn.leyou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class LeyouCorsConfiguration {
    @Bean
    public CorsFilter corsFilter(){

        //初始化cors配置对象
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了 *: 代表所有域名都可以跨域访问
        config.addAllowedOrigin("http://manage.leyou.cn");
        config.addAllowedOrigin("http://www.leyou.cn");
        //2) 是否发送Cookie信息
        config.setAllowCredentials(true);
        //3) 允许的请求方式("GET","POST","PUT","DELETE", "OPTIONS");
        config.addAllowedMethod("*");
        // 4）允许的头信息
        config.addAllowedHeader("*");

        //初始化cors配置源对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        //返回corsFilter实例，参数cors配置源对象
        return new CorsFilter(source);

    }
}
