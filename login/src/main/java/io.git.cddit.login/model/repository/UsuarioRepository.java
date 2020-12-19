package io.git.cddit.login.model.repository;

import io.git.cddit.login.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //select * from usuario where username =: login
    Optional<Usuario> findByUsername(String username);

    //select count(*) > 0 from usuario where username =:login
    boolean existsByUsername(String username);
}

//@Repository
//Associada com classes que isolam o acesso aos dados da sua aplicação. Comumente associada a DAO’s.
//Interface do marcador do repositório central. Captura o tipo de domínio a ser gerenciado, bem como o tipo de id do tipo de domínio.
// O objetivo geral é manter informações de tipo, bem como ser capaz de descobrir interfaces que estendem esta durante a varredura
// de caminho de classe para fácil criação de bean Spring.

//JpaRepository
//Spring Data JPA é um framework que nasceu para facilitar a criação dos nossos repositórios.
//
//Ele faz isso nos liberando de ter que implementar as interfaces referentes aos nossos repositórios (ou DAOs), e também já deixando
// pré-implementado algumas funcionalidades como, por exemplo, de ordenação das consultas e de paginação de registros.
//
//Ele (o Spring Data JPA) é, na verdade, um projeto dentro de um outro maior que é o Spring Data. O Spring Data tem por objetivo facilitar
// nosso trabalho com persistência de dados de uma forma geral. E além do Spring Data JPA, ele possui vários outros projetos:
//
//Spring Data Commons
//Spring Data Gemfire
//Spring Data KeyValue
//Spring Data LDAP
//Spring Data MongoDB
//Spring Data REST
//Spring Data Redis
//Spring Data for Apache Cassandra
//Ela tem todos os métodos que a gente precisa para fazer um CRUD (criar, ler, atualizar, deletar).
//Parâmetros de tipo:
//T - o tipo de domínio que o repositório gerencia (Usuario)
//ID - o tipo de id da entidade que o repositório gerencia (Integer)



