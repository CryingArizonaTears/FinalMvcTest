package com.senla.config;


import com.senla.api.service.IUserService;
import com.senla.security.filter.JwtFilter;
import com.senla.security.filter.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private IUserService userService;
    @Autowired
    private JwtFilter jwtFilter;

    protected void configure(HttpSecurity htpp) {
        try {
            htpp.httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/admin/*").hasRole("ADMIN")
                    .antMatchers("/user/*").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/registration", "/auth").permitAll()
                    .and()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void configure(HttpSecurity http) {
//        try {
//            http.csrf().disable()
//                    .authorizeRequests()
//                    .antMatchers("/login").permitAll()
//                    .antMatchers("signup").permitAll()
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    .anyRequest().authenticated()
//                    .and().httpBasic();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) {
//        try {
//            builder.userDetailsService(userService).passwordEncoder(passwordEncoder());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
