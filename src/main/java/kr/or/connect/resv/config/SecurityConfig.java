package kr.or.connect.resv.config;

import javax.sql.DataSource;
import kr.or.connect.resv.security.ResvUserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Log
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    ResvUserService resvUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("security config..............");

        //http.csrf().disable();

        http.authorizeRequests().antMatchers("/uploadmanager/**").hasRole("MANAGER");

        http.formLogin();
        //http.formLogin().loginPage("/login").successHandler(new LoginSuccessHandler());

        //http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        //http.exceptionHandling().accessDeniedPage("/accessDenied");

        http.logout().invalidateHttpSession(true);

        http.userDetailsService(resvUserService);
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
 /*       return new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }

        };*/
    }

}