package com.codeup.blog;

import com.codeup.blog.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersLoader)
                .passwordEncoder(passwordEncoder())
        ;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /********* Login Configuration *********/
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/posts")
                .permitAll()
                /********** Logout Configuration **********/
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                /********** Pages that can be viewed by anyone ************/
                .and()
                .authorizeRequests()
                .antMatchers("/", "/posts")
                .permitAll()
                /************** Pages that DO require authentication ***********/
                .and()
                .authorizeRequests()
                .antMatchers("/posts/create", "/posts/edit/{id}")
                .authenticated()
        ;

    }


}