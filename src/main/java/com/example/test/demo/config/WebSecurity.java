package com.example.test.demo.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //cors and csrf disabled for simplicity
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/admin.html").authenticated()
                .antMatchers(HttpMethod.POST, "/**").authenticated()
                .antMatchers(HttpMethod.POST, "/blogposts/*/comments").permitAll()
                .antMatchers(HttpMethod.POST, "/blogposts/*/comments/*/like").permitAll()
                .antMatchers(HttpMethod.DELETE, "/**").authenticated()
                .anyRequest().permitAll()
                .and().logout().logoutSuccessUrl("/").permitAll();
    }

    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin.html").authenticated()
            //.antMatchers(HttpMethod.POST, "/**").authenticated()
            //.antMatchers(HttpMethod.DELETE, "/**").authenticated()
            .antMatchers(HttpMethod.GET, "/**").permitAll()
            .and()
            .formLogin()
            .loginPage("/login.html").defaultSuccessUrl("/admin.html")
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }*/

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "OPTIONS"));

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /*
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
    */
}