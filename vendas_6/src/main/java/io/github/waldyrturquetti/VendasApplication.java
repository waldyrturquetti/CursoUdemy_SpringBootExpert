package io.github.waldyrturquetti;

import io.github.waldyrturquetti.domain.entity.Cliente;
import io.github.waldyrturquetti.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer {   //O SpringBootServletInitializer é necessário para o .war

    public static void main(String[] args) {

        SpringApplication.run(VendasApplication.class, args);
        //Ativar o MySQL e entrar na URL:http://localhost:8080/swagger-ui.html#/
    }
}
