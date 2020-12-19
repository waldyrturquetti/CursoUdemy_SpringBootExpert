package io.github.waldyrturquetti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@ComponentScan(basePackages = {"io.github.waldyrturquetti.repository" ,
//                                "io.github.waldyrturquetti.service"})       //Porém não necessario, o SpringBoot já escaneia todos os pacotes a
                                                                            //partir de io.github.waldyrturquetti, seria interesante usar
                                                                            // em casos de outos pacotes que se iniciam no diretório "Java".
@RestController
public class VendasApplication {

    //-------------------------------------------------------
    //@Autowired
    //@Qualifier("applicationName")
    //private String applicationName;

    //OU

    @Value("${application.name}") //necessário dessa varíavel em resources/application.properties
    private String applicationName;
    //---------------------------------------------------------

    //@Gato
    @Cachorro
    private Animal animal;

    @Bean(name = "executarAnimal")
    public CommandLineRunner executar(){
        return  args -> {
            this.animal.fazerBarulho();
        };
    }


    @GetMapping("/hello")
    public String helloWorld(){
        return applicationName;
        //return "Hello World";
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}