package io.git.cddit.login.config;

import io.git.cddit.login.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

//A WebSecurityConfigclasse é anotada com @EnableWebSecurity para habilitar o suporte de segurança da Web
// do Spring Security e fornecer a integração Spring MVC. Ele também estende WebSecurityConfigurerAdaptere substitui
// alguns de seus métodos para definir algumas especificações da configuração de segurança da web.
@EnableWebSecurity

//@EnableResourceServer
//A anotação @EnableResourceServer permite que nosso aplicativo se comporte como um  servidor de recursos.
//Um componente que requer um token de acesso para permitir, ou pelo menos considerar, o acesso aos seus recursos

//@EnableAuthorizationServer
//Por padrão, @EnableAuthorizationServer concede a um cliente acesso às credenciais do cliente
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //@Autowired
    //A anotação @ Autowired fornece controle sobre onde e como a ligação entre os beans deve ser realizada.
    // Pode ser usado para em métodos setter, no construtor, em uma propriedade ou métodos com nomes arbitrários
    // e / ou vários argumentos.

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {



//        auth.                                 //Dessa maneira inseri um usuario com os dados salvos em memória
//                inMemoryAuthentication()
//                .withUser("waldyrzao")
//                .password("123")
//                .roles("USER");
        auth
            .userDetailsService(usuarioService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors()
        .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
