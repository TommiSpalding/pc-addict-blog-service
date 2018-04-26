package com.example.test.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Web security conf for the blog site. Basically sets it up so unauthorized users can only post comments and likes and get everything but the admin page. Authorized users can post blogposts and delete comments etc.
 */
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //cors and csrf disabled for simplicity
        http.cors().disable().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/**.css", "/index.js", "/**.jpg", "/**.ico", "/blogposts/**").permitAll()
                .antMatchers(HttpMethod.POST, "/blogposts/*/comments").permitAll()
                .antMatchers(HttpMethod.POST, "/blogposts/*/comments/*/like").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    /**
     * Cors configuration. Disables cors for simplicity in this app.
     *
     * @return the cors configuration source
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "OPTIONS"));

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Sets up the admin user.
     */
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