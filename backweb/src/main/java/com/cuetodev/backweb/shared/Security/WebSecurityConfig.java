package com.cuetodev.backweb.shared.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // I use this method to have access in h2-console
    /*@Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/**");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v0/token", "/api/v0/checkToken").permitAll()
                .antMatchers("/api/v0/booking/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v0/bus/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v0/mails", "/api/v0/mail").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated();
    }
}
