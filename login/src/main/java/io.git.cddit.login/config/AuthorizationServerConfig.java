package io.git.cddit.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//@Configuration
//É uma annotation que indica que determinada classe possui métodos que expõe novos beans.
@Configuration

//Por padrão, @EnableAuthorizationServer concede a um cliente acesso às credenciais do cliente
//O Spring vem com um conjunto de anotações @Enable que tornam mais fácil para os desenvolvedores
//configurar um aplicativo Spring . Essas anotações são usadas em conjunto com a anotação @Configuration .
@EnableAuthorizationServer

//implements é usado quando uma classe implementa uma interface, extends é quando uma classe estende uma outra classe
// (concreta ou abstrata) ou quando uma interface estende uma outra interface.

public class  AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    //A anotação @Autowired delega ao Spring Boot a inicialização do objeto
    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${security.jwt.signing-key}") //Define a valor da variavel para esse atributo,
                                        // que tal valor está no application.properties
    private String signingKey;

    //@Bean
    //Por padrão, beans que usam @Bean são criados dentro de classes do tipo @Configuration
    //o @Bean é utilizado em cima dos métodos de uma classe, indicando que o Spring deve invocar
    //aquele método e gerenciar o objeto retornado por ele. Quando digo gerenciar é que agora
    //este objeto pode ser injetado em qualquer ponto da sua aplicação.
    @Bean
    public TokenStore tokenStore(){
        //TokenStore Persistence interface for OAuth2 tokens.

        //Uma JwtTokenStore é uma implementação que apenas lê dados dos próprios tokens. Não é realmente um store, já que nunca persiste
        // nada,e métodos como getAccessToken(OAuth2Authentication)sempre retornam nulo. No entanto, é uma ferramenta útil,
        //uma vez que traduz tokens de acesso e para autenticações. Use-o sempre que um TokenStore for necessário,
        //mas lembre-se de usar a mesma JwtAccessTokenConverter instância (ou uma com o mesmo verificador) que foi usada quando os
        //tokens foram criados.
        //O DefaultTokenServices usa o JwtTokenStore para persistir os tokens.

        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){

        // O JwtAccessTokenConverter usa o JKS public key.
        //O JwtTokenStore usa JwtAccessTokenConverter para ler os tokens.

        //JwtAccessTokenConverter
        //Ajudante que traduz entre valores de token codificados JWT e informações de autenticação OAuth (em ambas as direções).
        //Também atua como um TokenEnhancer quando os tokens são concedidos.

        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(signingKey);
        return tokenConverter;
    }

    //@Override Ele é uma forma de garantir que você está sobrescrevendo um método e não criando um novo.
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        //Endpoints
        //Os endpoints atuadores permitem monitorar e interagir com seu aplicativo. Spring Boot inclui vários
        // endpoints integrados e você também pode adicionar seus próprios. Por exemplo, o health endpoint fornece
        // informações básicas sobre a integridade do aplicativo.
        //A forma como os endpoints são expostos dependerá do tipo de tecnologia que você escolher. A maioria
        // dos aplicativos escolhe o monitoramento de HTTP, em que o ID do endpoint é mapeado para uma URL.
        // Por exemplo, por padrão, o health endpoint será mapeado para /health.

        //AuthorizationServerEndpointsConfigurer
        //AuthorizationServerEndpointsConfigurer configura a segurança dos pontos de extremidade oauth padrão,
        // por exemplo /oauth/token
        //Configura as propriedades e a funcionalidade aprimorada dos endpoints do Authorization Server.

        endpoints
            .tokenStore(tokenStore())
            .accessTokenConverter(accessTokenConverter())
            .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        //A classe Exception e suas subclasses são uma forma de Throwable que indica as condições que um aplicativo
        // razoável pode querer o catch.
        //A classe Exception e quaisquer subclasses que não sejam também subclasses de RuntimeException são
        // exceções verificadas . As exceções verificadas precisam ser declaradas em um método ou throws cláusula
        // do construtor se puderem ser lançadas pela execução do método ou construtor e se propagarem para fora
        // do método ou limite do construtor.

        //ClientDetailsServiceConfigurer define os detalhes para o acesso da aplicação cliente ao servidor de autenticação;

        clients
            .inMemory()
                //inMemory significa que todos os dados necessários para criar uma sessão
                // serão armazenados na memória. Ao reiniciar seu aplicativo, todos os dados
                // da sessão desaparecerão, o que significa que os usuários precisam fazer o login
                // e se autenticar novamente.
            .withClient("my-angular-app")
                //withCliente = login(nome);
            .secret("@321")
                //secret = senha;
            .scopes("read", "write")
                //scopes = permissões;
            .authorizedGrantTypes("password")
                //authorizedGrantTypes = tipo de autorização;
            .accessTokenValiditySeconds(1800);
                //tempo que o token é válido;
    }
}

//Bean
//@Component, @Service e @Repository são usados quando você quer que seus beans sejam auto configurados pelo spring.
//@Bean é usado quando você precisa explicitamente configurar o bean ao invés de deixar o spring automaticamente fazer.
//Por padrão, os beans no spring são singletons, ou seja, uma instância por container context.
//@autowired é como você injeta, ou seja, usa seus beans pelo projeto.
//
//As vezes precisamos ter beans cujas classes nós não podemos anotar.
//
//Vamos supor uma classe ArquivoNuvem que não está em nosso projeto e que gostaríamos que ela fosse passível de ser injetada.
// Como ela está em um outro projeto, não podemos anotar ela com estereótipo algum.
//
//O que fazer nesse caso?
//
//Podemos criar um método produtor utilizando a anotação @Bean dentro de uma classe de configuração.. Veja:
//

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AplicacaoConfig {
//
//  @Bean
//  public ArquivoNuvem arquivoNuvem() {
//    ArquivoNuvem nuvem = new ArquivoNuvem();
//    return nuvem;
//  }
//}
//Com a configuração acima, podemos injetá-la em nossa classe de serviço normalmente:
//
//
//public class ClienteServico {
//
//  @Autowired
//  private ArquivoNuvem nuvem;
//
//  @Autowired
//  private ClienteRepositorio repositorio;
//}


//Configure
//Para realizar a configuração do servidor, precisamos sobrescrever três métodos. Todos possuem o nome
// configure, mas cada um recebe um parâmetro diferente:
//1
//AuthorizationServerSecurityConfigurer define as configurações de segurança nos endpoints relativos
// aos tokens de acesso;
//2
//ClientDetailsServiceConfigurer define os detalhes para o acesso da aplicação cliente ao servidor
// de autenticação;
//3
//AuthorizationServerEndpointsConfigurer, que define configurações para os endpoints de autenticação
// e geração de tokens.