package com.capstone.precare.config;

import com.capstone.precare.mapper.UsersMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * 스프링 시큐리티 설정
 *
 * created 18.08.31  by 임지훈
 */

@MapperScan("com.capstone.precare.mapper")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    protected DataSource dataSource;
    @Value("${spring.queries.users-query}")
    private String usersQuery;
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;


    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/images/**", "/js/**", "/plugins/**", "/styles/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                //구매내역 페이지는 로그인한 사람만 들어갈 수 있도록 인증 구현
                .antMatchers("/checkout**").hasRole("USER")
                .antMatchers("/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .usernameParameter("user_id")
                .passwordParameter("user_pwd")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/unauthorize");
    }

    //로그인(인증) 처리
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }






}
