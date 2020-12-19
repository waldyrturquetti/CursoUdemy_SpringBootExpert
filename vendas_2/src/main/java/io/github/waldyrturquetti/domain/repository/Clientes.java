package io.github.waldyrturquetti.domain.repository;

import io.github.waldyrturquetti.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {

    //Usando o JPA de uma outra maneira (Spring Data)

    // select c from Cliente where c.nome like :nome;
    //Equivalente a esse find
    List<Cliente> findByNomeLike(String nome);
    //ou
    //@Query(value = "select c from Cliente c where c.nome like :nome")
    //List<Cliente> encontrarPorNome( @Param("nome") String nome);

    //SQL nativo
    @Query(value = "select * from Cliente c where c.nome like '%:nome%'", nativeQuery = true)
    List<Cliente> encontrarPorNome( @Param("nome") String nome);

    // select c from Cliente where c.nome = :nome or c.id = id
    //ORDERBY ID;
    //List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);

    Cliente findOneByNome(String nome);

    boolean existsByNome(String nome);

    @Query("delete from Cliente c where c.nome =:nome")
    @Modifying                      //Obrigatoria quando for fazer update ou delete
    void deleteByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos p where c.id =:id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

}
