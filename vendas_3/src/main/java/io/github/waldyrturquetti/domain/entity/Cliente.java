package io.github.waldyrturquetti.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity //Para usar o JPA, mas não é necessário para o JDBC
@Table(name = "cliente") //é necessário apenas quando a tabela está com um nome diferente da classe

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {

    @Id     //JPA
    @GeneratedValue(strategy = GenerationType.AUTO)     //JPA
    @Column(name = "id")        //JPA
    private Integer id;

    @Column(name = "nome", length = 100)      //JPA
    private String nome;

    @Column(name = "cpf", length = 11)
    private String cpf;

//    public String getCpf() {
//        return cpf;
//    }
//
//    public void setCpf(String cpf) {
//        this.cpf = cpf;
//    }

    @JsonIgnore         //Faz com que o json ignore essa propriedade
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY) //O EAGER iria trazer todos os pedidos que esse cliente fez
    private Set<Pedido> pedidos;                             //O LAZY só o pedido em questão


//    public Cliente() {
//    }

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

//    public Cliente(String nome) {
//        this.nome = nome;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    @Override
//    public String toString() {
//        return "Cliente{" +
//                "id=" + id +
//                ", nome='" + nome + '\'' +
//                ", cpf='" + cpf + '\'' +
//                '}';
//    }
//
//    public Set<Pedido> getPedidos() {
//        return pedidos;
//    }
//
//    public void setPedidos(Set<Pedido> pedidos) {
//        this.pedidos = pedidos;
//    }
}