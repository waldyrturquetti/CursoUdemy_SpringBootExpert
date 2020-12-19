package io.github.waldyrturquetti.config;

import io.github.waldyrturquetti.security.jwt.JwtAuthFilter;
import io.github.waldyrturquetti.security.jwt.JwtService;
import io.github.waldyrturquetti.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;
    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder(){

//        PasswordEncoder passwordEncoder = new PasswordEncoder() {     //Não precisamos usar, pois já vem no security.
//
//            @Override
//            public String encode(CharSequence charSequence) {
//                return charSequence + "321";
//            }
//
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                return (charSequence + "321").equals(s);
//            }
//        }

        return new BCryptPasswordEncoder();         //Rash
    }

    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("waldyrzao")
//                .password(passwordEncoder().encode("123"))
//                .roles("USER", "ADMIN");

        auth                                        //Agora usamos o UsuarioServiceImpl
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()           //O csrf traz a segurança entre o back e o front
                .authorizeRequests()
                    .antMatchers("/api/clientes/**")
                        .hasRole("USER")  //Role seria o perfil do usuário.
                        //.hasAuthority("MANTER USUARIO")
                        //.authenticated()  //Faz necessário a autenticação
                        //.permitAll()        //permite qualquer um entrar
                    .antMatchers("/api/produtos/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers("/api/pedidos/**")
                        .hasAnyRole("USER")     //Diz que o usuario comum e o ADMIN tem acesso.
                    .antMatchers(HttpMethod.POST, "/api/usuarios/**")
                        .permitAll()
                    .anyRequest().authenticated()
                .and()
                    //.formLogin();           //Podemos definir um login personalizado com um formulário.
                    //.httpBasic();           //Usando o Basic
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore( jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        ;
    }


}
