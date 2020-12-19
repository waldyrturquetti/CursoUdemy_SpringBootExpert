package io.git.cddit.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

//A anotação @EnableResourceServer permite que nosso aplicativo se comporte como um  servidor de recursos.
//Um componente que requer um token de acesso para permitir, ou pelo menos considerar, o acesso aos seus recursos
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //Aqui é as configurações dos Resources(API's),
    @Override
    public void configure(HttpSecurity http) throws Exception {

        //HttpSecurity
        //A HttpSecurity é semelhante ao elemento XML <http> do Spring Security na configuração do namespace. Ele permite configurar a segurança
        // baseada na web para solicitações http específicas. Por padrão, ele será aplicado a todas as solicitações, mas pode ser restringido usando
        // requestMatcher (RequestMatcher) ou outros métodos semelhantes.

        http
                // = autorizar pedidos
            .authorizeRequests()
                //Permite restringir o acesso com base nas implementações de HttpServletRequest
                // uso RequestMatcher(ou seja, por meio de padrões de URL).

                .antMatchers("/api/clientes/**")
                //Permite configurar o HttpSecurity para ser invocado apenas para corresponder ao padrão de ant(Apache ant) fornecido. Se uma
                // configuração mais avançada for necessária, considere usar requestMatchers() ou requestMatcher(RequestMatcher).
                //Invocando antMatcher(String) substituirá invocações anteriores mvcMatcher(String)}, requestMatchers(), antMatcher(String),
                // regexMatcher(String), e requestMatcher(RequestMatcher).

                        // = Autenticado
                    .authenticated()
                        //Para obter o pedido (ou seja, acessar o caminho especificado no antMatchers) tem que estar autentica,
                        //no caso dessa aplicação é o token estar válido.

                .antMatchers("/api/usuarios/**").authenticated()

                //.anyRequest() // = Qualquer outro Pedido
                // .denyAll() // = Bloqueia tudo.

                .anyRequest().authenticated()

            .and()

                .formLogin() //Um formulário de login simples, que usamos em localhost:8080/login

                .defaultSuccessUrl("https://cddit.com.br/", true)
                //Colocando as credenciais corretas irá ser encaminhado para a
                //URL especifica, o true indica que sempre será assim.

                .permitAll(); //Permite todos, estando ou não autenticados a acessar o caminho, nesse caso o login

    }
}


