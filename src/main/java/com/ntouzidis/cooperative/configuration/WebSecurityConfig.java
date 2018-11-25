package com.ntouzidis.cooperative.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
//        auth.jdbcAuthentication().dataSource(securityDataSource);
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // use jdbc authentication ... oh yeah!!!
//        auth.jdbcAuthentication().dataSource(securityDataSource);
//        auth.userDetailsService(userDetailsService);
//    }

//     @Bean
//    public UserDetailsManager userDetailsManager() {
//        CustomUserDetailsManager detailsManager = new CustomUserDetailsManager();
////        jdbcUserDetailsManager.setDataSource(securityDataSource);
////        jdbcUserDetailsManager.setEnableAuthorities(true);
//        return detailsManager;
//    }



//    @Bean
//    public Authentication authentication() throws Exception {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
//    }
//
//    @Bean(name="authenticationManager")
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    // Expose the UserDetailsService as a Bean
//    @Bean
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return super.userDetailsServiceBean();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/welcome/**").permitAll()
//                .antMatchers("/**").hasAnyRole("CUSTOMER", "MEMBER", "ADMIN")
                .antMatchers("/dashboard/**").hasAnyRole("CUSTOMER", "TRADER", "ADMIN")
                .antMatchers("/trade/**").hasAnyRole("TRADER", "ADMIN")
                .antMatchers("/copy/**").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers("/user/**").hasAnyRole("CUSTOMER", "TRADER", "ADMIN")
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/management-panel/**").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/offers/**").hasRole("MEMBER")
                .antMatchers("/order/**").denyAll()
//                .antMatchers("/order/**").hasRole("CUSTOMER")
                .antMatchers("/cart/**").hasRole("CUSTOMER")
                .antMatchers("/email/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

                http.formLogin().defaultSuccessUrl("/trade", true);
    }



//    @Bean(name = "javamelodyFilter")
//    public FilterRegistrationBean<MonitoringFilter> javamelodyFilterBean() {
//        FilterRegistrationBean<MonitoringFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new MonitoringFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("javamelodyFilter");
//        registration.setAsyncSupported(true);
//        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
//        return registration;
//    }

//    @Bean(name = "javamelodySessionListener")
//    public ServletListenerRegistrationBean<SessionListener> sessionListener() {
//        return new ServletListenerRegistrationBean<>(new SessionListener());
//    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("thanosntouzidis@gmail.com");
        mailSender.setPassword("jxfgbvnycnifbund");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                user.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }


}
