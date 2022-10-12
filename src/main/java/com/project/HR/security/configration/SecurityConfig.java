package com.project.HR.security.configration;
import com.project.HR.security.Filter.CustomAuthenticationFilter;
import com.project.HR.security.Filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/employee/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/employee/login/**").permitAll();

        http.authorizeRequests().antMatchers(GET, "/employee/**").hasAuthority("Hr"); //GET EMPLOYEE
        http.authorizeRequests().antMatchers(POST, "/employee/").hasAuthority("Hr"); //ADD EMPLOYEE
        http.authorizeRequests().antMatchers(DELETE, "/employee/**").hasAuthority("Hr"); //DELETE EMPLOYEE
        http.authorizeRequests().antMatchers(GET, "/employee/team/**").hasAuthority("Hr"); //GET EMPLOYEES IN TEAM
        http.authorizeRequests().antMatchers(GET, "/employee/salary/**").hasAnyAuthority("Employee","Hr"); //GET EMPLOYEE SALARY
        http.authorizeRequests().antMatchers(PUT, "/employee/").hasAuthority("Hr"); //UPDATE EMPLOYEE
        http.authorizeRequests().antMatchers(GET, "/employee/employeesUnderManager/**").hasAuthority("Hr"); //GET EMPLOYEES UNDER MANGER REC
        http.authorizeRequests().antMatchers(GET, "/employee/manager/**").hasAuthority("Hr"); //GET EMPLOYEES UNDER MANGER

        http.authorizeRequests().antMatchers(POST, "/leave").hasAuthority("Employee"); //ADD EMPLOYEE LEAVES
        http.authorizeRequests().antMatchers(GET, "/leave/**").hasAuthority("Hr"); //GET EMPLOYEE LEAVES

        http.authorizeRequests().antMatchers(POST, "/raises").hasAuthority("Hr"); //ADD EMPLOYEE Raises
        http.authorizeRequests().antMatchers(GET, "/raises/**").hasAuthority("Hr"); //GET EMPLOYEE Raises

        http.authorizeRequests().antMatchers(POST, "/bonus").hasAuthority("Hr"); //ADD EMPLOYEE BONUS
        http.authorizeRequests().antMatchers(GET, "/bonus/**").hasAuthority("Hr"); //GET EMPLOYEE BONUS

        http.authorizeRequests().antMatchers(GET, "/salaryHistory/**").hasAnyAuthority("Employee","Hr"); //GET EMPLOYEE SALARY HISTORY

        http.authorizeRequests().antMatchers(GET, "/employee/user/**").hasAuthority("Hr"); //GET USER
        http.authorizeRequests().antMatchers(POST, "/employee/user").hasAuthority("Hr"); //ADD USER

        http.authorizeRequests().antMatchers(GET, "/role/").hasAuthority("Hr"); //GET All ROLE
        http.authorizeRequests().antMatchers(POST, "/role/").hasAuthority("Hr"); //ADD ROLE

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
