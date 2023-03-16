package kr.or.connect.resv.config;

import kr.or.connect.resv.config.security.ResvUserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log
@EnableWebSecurity
@ComponentScan(basePackages = { "kr.or.connect.resv.config.security" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	ResvUserService resvUserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("security config...................");

		http.csrf().disable();

		http.authorizeRequests().antMatchers("/uploadmanager/**").hasRole("MANAGER").and().formLogin();
		http.userDetailsService(resvUserService);

		http.headers().frameOptions().sameOrigin();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		log.info("build Auth global.........");

		auth.userDetailsService(resvUserService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {

			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.equals(encodedPassword);
			}

		};
	}
}
