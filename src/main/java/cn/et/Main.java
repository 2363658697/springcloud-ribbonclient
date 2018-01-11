package cn.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@RibbonClient(value="SENDMAIL")   //表示当前服务需要调用的其他服务的名称
@EnableEurekaClient //该注解继承了@EnableDiscoveryClient
@SpringBootApplication //该注解继承了@Configuration
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
