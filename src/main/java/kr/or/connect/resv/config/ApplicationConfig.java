package kr.or.connect.resv.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"kr.or.connect.resv.dao", "kr.or.connect.resv.service"})
@Import({ DBConfig.class})
public class ApplicationConfig {
}
