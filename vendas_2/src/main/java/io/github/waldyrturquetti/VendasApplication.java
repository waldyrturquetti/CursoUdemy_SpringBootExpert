package io.github.waldyrturquetti;

import io.github.waldyrturquetti.domain.entity.Cliente;
import io.github.waldyrturquetti.domain.entity.Pedido;
import io.github.waldyrturquetti.domain.repository.Clientes;
import io.github.waldyrturquetti.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(
            @Autowired Clientes clientes,
            @Autowired Pedidos pedidos
    ){
        return args -> {
            System.out.println("Salvando Clientes");
            //clientes.save(new Cliente("Waldyrzao"));
            //clientes.save(new Cliente("Outro Cliente"));

            Cliente brabo = new Cliente("Waldyrzao");
            clientes.save(brabo);

            Pedido p = new Pedido();
            p.setCliente(brabo);
            p.setDatapedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));
            pedidos.save(p);

            //boolean existe = clientes.existsByNome("Waldyrzao");
            //System.out.println("existe um cliente com o nome Waldyrzao?"+ existe);


//            List<Cliente> todosClientes = clientes.findAll();
//            todosClientes.forEach(System.out::println);

//            List<Cliente> result = clientes.encontrarPorNome("GAY");
//            result.forEach(System.out::println);

//            Cliente cliente = clientes.findClienteFetchPedidos(brabo.getId());
//            System.out.println(cliente);
//            System.out.println(cliente.getPedidos());

            pedidos.findByCliente(brabo).forEach(System.out::println);


//            System.out.println("Atualizando clientes");
//            todosClientes.forEach(c -> {
//                c.setNome(c.getNome() + " atualizado.");
//                clientes.save(c);
//            });
//
//            todosClientes = clientes.findAll();
//            todosClientes.forEach(System.out::println);
//
//            System.out.println("Buscando clientes");
//            clientes.findByNomeLike("Cli").forEach(System.out::println);
//
//            System.out.println("deletando clientes");
//            clientes.findAll().forEach(c -> {
//                clientes.delete(c);
//            });
//
//            todosClientes = clientes.findAll();
//            if(todosClientes.isEmpty()){
//                System.out.println("Nenhum cliente encontrado.");
//            }else{
//                todosClientes.forEach(System.out::println);
//            }
       };

    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
