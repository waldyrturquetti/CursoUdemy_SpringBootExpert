package io.github.waldyrturquetti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
//@Profile("devlopment")      //Isso diz que só vai funcionar no ambiente de desenvolvimento
//OU
@Devlopment                  //Assim conseguimos definir as configurações apenas utilizando uma Annotatian
public class MinhaConfiguration {

    //@Bean(name = "applicationName")       //Usamos agora o resources/application.properties
    //public String applicationName(){
    //    return "Sistema de Vendas";
    //}

    /*
    @Bean(name = "outraConfiguracao")
    public String outraConfiguracao(){
        return "Sistema de Vendas";
    }
     */

    @Bean
    public CommandLineRunner executar(){
        return args -> {
            System.out.println("RODANDO A CONFIGURAÇÃO DE DESENVOLVIMENTO");
        };
    }
}
