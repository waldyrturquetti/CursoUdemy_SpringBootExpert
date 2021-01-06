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

//A WebSecurityConfigclass e é anotada com @EnableWebSecurity para habilitar o suporte de segurança da Web
// do Spring Security e fornecer a integração Spring MVC. Ele também estende WebSecurityConfigurerAdapter e substitui
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

        //SecurityBuilder usado para criar um AuthenticationManager. Permite criar facilmente autenticação de memória,
        // autenticação LDAP, autenticação baseada em JDBC, adição UserDetailsService e adição AuthenticationProvider.


//        auth.                                 //Dessa maneira inseri um usuario com os dados salvos em memória
//                inMemoryAuthentication()
//                .withUser("waldyrzao")
//                .password("123")
//                .roles("USER");
        auth
            .userDetailsService(usuarioService)
                //Interface central que carrega dados específicos do usuário.
                //É usado em toda a estrutura como um DAO do usuário e é a estratégia usada pelo DaoAuthenticationProvider.
                //A interface requer apenas um método somente leitura, o que simplifica o suporte para novas estratégias de acesso a dados.

            .passwordEncoder(passwordEncoder());
                ////Interface de serviço para codificação de senhas. A implementação preferida é BCryptPasswordEncoder.
    }
    //https://spring.io/guides/topicals/spring-security-architecture
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        //Processa um Authentication pedido. (Mais informação ABAIXO)

        return super.authenticationManager();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                //Adiciona suporte aos ataque CSRF.
                .disable()
                    //o suporte CSRF é disabilitado
            .cors()
                //Adiciona um CorsFilter para ser usado. Se um bean com o nome de corsFilter for fornecido, o CorsFilter será usado.
                // Caso contrário, se corsConfigurationSource for definido, o CorsConfiguration será usado. Caso contrário, se Spring MVC estiver
                // no caminho de classe, a HandlerMappingIntrospector será usado.
        .and()

            .sessionManagement()
                    //Permite configurar o Gerenciamento de Sessão.

                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    //Especifica as várias políticas de criação de sessão para Spring Security.
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //Interface de serviço para codificação de senhas. A implementação preferida é BCryptPasswordEncoder.

        return NoOpPasswordEncoder.getInstance();
        //Um codificador de senha que não faz nada. Útil para teste onde trabalhar com senhas de texto simples pode ser preferido.
    }
}

//configure (AuthenticationManagerBuilder) é usado para estabelecer um mecanismo de autenticação, permitindo que AuthenticationProviders
// sejam adicionados facilmente: por exemplo, o seguinte define a autenticação na memória com os logins embutidos de 'usuário' e 'admin'.
//public void configure(AuthenticationManagerBuilder auth) {
//    auth
//        .inMemoryAuthentication()
//        .withUser("user")
//        .password("password")
//        .roles("USER")
//    .and()
//        .withUser("admin")
//        .password("password")
//        .roles("ADMIN","USER");
//}


//configure (HttpSecurity) permite a configuração de segurança baseada na web em um nível de recurso, com base em uma combinação de
// seleção - por exemplo, o exemplo abaixo restringe os URLs que começam com / admin / para usuários que têm função ADMIN e declara que
// quaisquer outros URLs precisam ser autenticado com sucesso.
//protected void configure(HttpSecurity http) throws Exception {
//    http
//        .authorizeRequests()
//        .antMatchers("/admin/**").hasRole("ADMIN")
//        .anyRequest().authenticated()
//}


//configure (WebSecurity) é usado para configurações que impactam a segurança global (ignorar recursos,
// definir modo de depuração, rejeitar solicitações implementando uma definição de firewall personalizada). Por exemplo,
// o método a seguir faria com que qualquer solicitação que comece com / resources / fosse ignorada para fins de autenticação.
//public void configure(WebSecurity web) throws Exception {
//    web
//        .ignoring()
//        .antMatchers("/resources/**");
//}

//AuthenticationManager
//A principal interface de estratégia para autenticação é AuthenticationManager, que possui apenas um método:
//
//public interface AuthenticationManager {
//
//  Authentication authenticate(Authentication authentication)
//    throws AuthenticationException;
//}
//Um AuthenticationManagerpode fazer uma das três coisas em seu authenticate()método:
//
//Retorne um Authentication(normalmente com authenticated=true) se puder verificar se a entrada representa um principal válido.
//
//Lance um AuthenticationExceptionse ele acredita que a entrada representa um principal inválido.
//
//Retorne null se não puder decidir.
//
//AuthenticationException é uma exceção de tempo de execução. Geralmente, ele é tratado por um aplicativo de forma genérica, dependendo
// do estilo ou da finalidade do aplicativo. Em outras palavras, normalmente não se espera que o código do usuário o capture e manipule.
// Por exemplo, uma IU da web pode renderizar uma página que diz que a autenticação falhou e um serviço HTTP de back-end pode enviar uma
// resposta 401, com ou sem um WWW-Authenticatecabeçalho, dependendo do contexto.
//
//A implementação mais comumente usada de AuthenticationManager é ProviderManager, que delega para uma cadeia de
// AuthenticationProvider instâncias. Um AuthenticationProvider é um pouco parecido com um AuthenticationManager,
// mas tem um método extra para permitir que o autor da chamada pergunte se ele suporta um determinado Authenticationtipo:
//
//public interface AuthenticationProvider {
//
//	Authentication authenticate(Authentication authentication)
//			throws AuthenticationException;
//
//	boolean supports(Class<?> authentication);
//}
//O Class<?>argumento no supports()método é realmente Class<? extends Authentication>(só é perguntado se ele suporta algo que é passado
// para o authenticate() método). A ProviderManagerpode oferecer suporte a vários mecanismos de autenticação diferentes no mesmo aplicativo,
// delegando a uma cadeia de AuthenticationProviders. Se um ProviderManagernão reconhecer um Authenticationtipo específico de instância,
// ele será ignorado.
//
//A ProviderManager tem um pai opcional, que pode consultar se todos os provedores retornarem null. Se o pai não estiver disponível,
// um null Authenticationresulta em um AuthenticationException.
//
//Às vezes, um aplicativo possui grupos lógicos de recursos protegidos (por exemplo, todos os recursos da web que correspondem
// a um padrão de caminho, como /api/**) e cada grupo pode ter seu próprio dedicado AuthenticationManager. Freqüentemente, cada um
// deles é um ProviderManagere eles compartilham um dos pais. O pai é, então, uma espécie de recurso “global”, atuando
// como reserva para todos os provedores.
//
//Spring Security fornece alguns auxiliares de configuração para obter rapidamente recursos comuns do gerenciador de autenticação
// configurados em seu aplicativo. O auxiliar mais comumente usado é o AuthenticationManagerBuilder, que é ótimo para configurar
// detalhes do usuário na memória, JDBC ou LDAP ou para adicionar um personalizado UserDetailsService. O exemplo a seguir mostra
// um aplicativo que configura o global (pai) AuthenticationManager:
//
//@Configuration
//public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
//
//   ... // web stuff here
//
//  @Autowired
//  public void initialize(AuthenticationManagerBuilder builder, DataSource dataSource) {
//    builder.jdbcAuthentication().dataSource(dataSource).withUser("dave")
//      .password("secret").roles("USER");
//  }
//
//}

//CSRF
//Quando usar a proteção CSRF
//Quando você deve usar a proteção contra CSRF? Nossa recomendação é usar proteção CSRF para qualquer solicitação que possa ser processada
// por um navegador por usuários normais. Se você estiver criando um serviço que é usado apenas por clientes sem navegador, provavelmente7
// desejará desativar a proteção CSRF.

//CORS
//CORS - Cross-Origin Resource Sharing (Compartilhamento de recursos com origens diferentes) é um mecanismo que usa cabeçalhos adicionais
// HTTP para informar a um navegador que permita que um aplicativo Web seja executado em uma origem (domínio) com permissão para acessar
// recursos selecionados de um servidor em uma origem distinta. Um aplicativo Web executa uma requisição cross-origin HTTP ao solicitar
// um recurso que tenha uma origem diferente (domínio, protocolo e porta) da sua própria origem.


//SessionCreationPolicy.STATELESS
//ALWAYS
//Sempre crie um HttpSession
//IF_REQUIRED
//Spring Security só criará um HttpSessionse necessário
//NEVER
//Spring Security nunca criará um HttpSession, mas usará o HttpSessionse ele já existir
//STATELESS
//Spring Security nunca criará um HttpSessione nunca o usará para obter oSecurityContext