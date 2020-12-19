package io.git.cddit.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Clock;

//@SpringBootApplication
//Ela engloba a @Component, @ComponentScan e mais uma chamada @EnableAutoConfiguration, que é utilizada pelo
//Spring Boot para tentar advinhar as configurações necessárias para rodar o seu projeto.
//@Component
//A annotation básica que indica que uma classe vai ser gerenciada pelo container do Spring.
//@ComponentScan
//Em geral você a usa em classes de configuração(@Configuration) indicando quais pacotes ou classes
// devem ser scaneadas pelo Spring para que essa configuração funcione.

//Ative a configuração automática do Contexto do Aplicativo Spring, tentando adivinhar e
// configurar os beans que você provavelmente precisará. As classes de configuração automática
// geralmente são aplicadas com base em seu caminho de classe e nos beans que você definiu.
// Por exemplo, se você tiver tomcat-embedded.jarem seu caminho de classe, provavelmente
// desejará um TomcatServletWebServerFactory(a menos que tenha definido seu próprio
// ServletWebServerFactorybean).
//Ao usar @SpringBootApplication, a configuração automática do contexto é ativada automaticamente e
// adicionar esta anotação não tem, portanto, nenhum efeito adicional.
//A configuração automática tenta ser o mais inteligente possível e recua conforme você define mais
// de sua própria configuração. Você sempre pode manualmente exclude()qualquer configuração
// que você nunca deseja aplicar (use excludeName()se você não tiver acesso a eles).
// Você também pode excluí-los por meio da spring.autoconfigure.excludepropriedade.
// A configuração automática é sempre aplicada após o registro dos beans definidos pelo usuário.
//O pacote da classe com a qual é anotado @EnableAutoConfiguration, geralmente via @SpringBootApplication,
// tem um significado específico e é freqüentemente usado como um 'padrão'. Por exemplo, ele será usado
// durante a verificação de @Entityclasses. Geralmente é recomendado que você coloque
// @EnableAutoConfiguration(se não estiver usando @SpringBootApplication) em um pacote raiz para
// que todos os subpacotes e classes possam ser pesquisados.
//As classes de configuração automática são @Configuration beans Spring regulares . Eles são localizados usando
// o SpringFactoriesLoadermecanismo (com chave nesta classe). Geralmente os beans de autoconfiguração são
// @Conditionalbeans (mais frequentemente usando @ConditionalOnClasse @ConditionalOnMissingBeananotações).

@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) {
        //A SpringApplication.run é resposável por
        //Estar iniciando a aplicação SpringBoot
        SpringApplication.run(LoginApplication.class, args);

        //System.out.println(LoginApplication.class); // Print = "class io.git.cddit.login.LoginApplication"
    }
}

//Link recomendado:
//https://imasters.com.br/desenvolvimento/autenticacao-com-jwt-no-spring-boot

//@Component
//Anotação genérica para qualquer componente gerenciado pelo Spring.
//Esta anotação faz com que o bean registrado no Spring possa ser utilizado
//em qualquer bean, seja ele um serviço, um DAO, um controller, etc. No nosso exemplo,
// ele será responsável por um Bean que representa uma entidade.

//@Repository
//Anotação que serve para definir uma classe como pertencente à camada de persistência.

//@Service
//Anotação que serve para definir uma classe como pertencente à camada de Serviço da aplicação.

//@Autowired
//A anotação @ Autowired fornece controle sobre onde e como a ligação entre os beans deve ser realizada.
// Pode ser usado para em métodos setter, no construtor, em uma propriedade ou métodos com nomes arbitrários
// e / ou vários argumentos.
//O @Autowired permitem que o Spring injete as dependências na classe

//@Bean
//No Spring, os objetos que formam a espinha dorsal da sua aplicação e que sejam gerenciados pelo Spring são
// chamados de beans. Um bean é um objeto que é instanciado, montado e gerenciado pelo Spring IoC container.
//Existem diversas forma de se criar beans no Spring, você pode criar classes anotadas com @Component,
// @Configuration ou @Service para serem gerenciadas pelo Spring. Assim como pode usar o @Bean em um método,
// e tornar a instância retornada pelo método como um objeto gerenciado pelo Spring (seja de uma classe própria
// ou de terceiros).

