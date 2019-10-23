package com.immoc.servier.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableJdbcHttpSession
public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;


//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }




    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/imooc-signIn.html").loginProcessingUrl("/login")
                .and().logout().logoutSuccessHandler(logoutSuccessHandler)
                .and().authorizeRequests().antMatchers("/imooc-signIn.html").permitAll().anyRequest().authenticated()
                .and().csrf().disable();
//        http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic()
//                .and().logout().logoutSuccessHandler(logoutSuccessHandler);



    }
}
