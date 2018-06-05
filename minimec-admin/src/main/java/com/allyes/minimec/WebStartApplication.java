package com.allyes.minimec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 
 * @author liuwei
 * @date 2018-03-10
 * 管理系统启动
 */
@SpringBootApplication
@MapperScan(basePackages = "com.allyes.minimec.domain.mapper")
public class WebStartApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(WebStartApplication.class, args);
		
	}

}
