package io.github.waldyrturquetti.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")

//@Getter     //Ele Gera na hora da compilação os getter
//@Setter     //Ele Gera na hora da compilação os setter
@Data         //Ele Gera na hora da compilação os getter e setter
@NoArgsConstructor      //Gera os construtores
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;

    @Column(name = "preco_unitario")
    @NotNull(message = "{campo.preco.obrigatorio}")
    private BigDecimal preco;


//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getDescricao() {
//        return descricao;
//    }
//
//    public void setDescricao(String descricao) {
//        this.descricao = descricao;
//    }
//
//    public BigDecimal getPreco() {
//        return preco;
//    }
//
//    public void setPreco(BigDecimal preco) {
//        this.preco = preco;
//    }
//
//    @Override
//    public String toString() {
//        return "Produto{" +
//                "id=" + id +
//                ", descricao='" + descricao + '\'' +
//                ", preco=" + preco +
//                '}';
//    }
}
