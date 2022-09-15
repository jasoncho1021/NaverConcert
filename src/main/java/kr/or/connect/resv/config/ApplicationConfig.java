package kr.or.connect.resv.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "kr.or.connect.resv.dao", "kr.or.connect.resv.service",
		"kr.or.connect.resv.manager.dao", "kr.or.connect.resv.manager.service" })
@Import({ DBConfig.class, SecurityConfig.class })
public class ApplicationConfig {
}
