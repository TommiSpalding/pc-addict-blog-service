package com.example.test.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin.html").authenticated()
            .antMatchers(HttpMethod.POST, "/**").authenticated()
            .antMatchers(HttpMethod.DELETE, "/**").authenticated()
            .antMatchers(HttpMethod.GET, "/**").permitAll()
            .and()
            .formLogin()
            .loginPage("/login.html").defaultSuccessUrl("/admin.html")
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("taikaviitta")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}