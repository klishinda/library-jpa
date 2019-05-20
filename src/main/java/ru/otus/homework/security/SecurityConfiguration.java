package ru.otus.homework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers(HttpMethod.GET,"/api/books").authenticated()
                .antMatchers("/initialization").hasRole("ADMIN")
                .antMatchers("/add-book").hasRole("ADMIN")
                .antMatchers("/update-book").hasRole("ADMIN")
                .antMatchers("/delete-book").hasRole("ADMIN")
                //.antMatchers("/actuator*").hasRole("ADMIN")
                .antMatchers("/actuator/hystrix.stream").permitAll()
                .antMatchers("/actuator/*").hasRole("ADMIN")
                .antMatchers("/hystrix/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN")
                .and()
                .rememberMe().key("SecurityRememberMeKey")
                .tokenValiditySeconds(3600)
                .alwaysRemember(true)
                .and()
                .formLogin()
                .and()
                .exceptionHandling().accessDeniedPage("/access-error");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}