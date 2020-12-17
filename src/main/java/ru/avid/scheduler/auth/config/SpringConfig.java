package ru.avid.scheduler.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
public class SpringConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //на время разработки отключить, чтобы не было ошибок доступа (чтоб не ожидал токен)
        http.formLogin().disable();//форма авторизации будет не на спринг
        http.httpBasic().disable(); //откл. чтандартную форму авторизации
        http.requiresChannel().anyRequest().requiresSecure(); // испозуем https
    }
}
