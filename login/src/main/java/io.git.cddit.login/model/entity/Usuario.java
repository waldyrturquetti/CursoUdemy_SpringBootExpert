package io.git.cddit.login.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity //Define que essa classe é uma entidade
@Data
@NoArgsConstructor
public class Usuario {

    @Id //com essa injeção de dependencia o spring configura essa coluna pra ser a de ID.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Essa injeção gera os valores para os id e garante que eles não
    private Integer id;                                 //se repitam.

    //@Email              //Verifica se o email está no fromato correto
    @Column(unique = true, name = "login") //Define o nome da coluna no banco e o unique garante que um valor de campo seja único.
    @NotEmpty(message = "{campo.login.obrigatorio}") //caso a informação desse atributo esteja vazio no JSON
                                                     //é enviado a mensagem dessa variavel em messages.properties
    private String username;

    @Column(name = "senha")
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String password;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

//Lombok
//Lombok é uma biblioteca que ajuda a manter o código limpo e conciso, diminuindo sua
//verbosidade por meio de anotações. O Lombok possui uma série de anotações que visam diminuir a
//quantidade de código “boilerplate”. Alguns exemplos são:
//@Getter e @Setter: gerar getters e setters para campos ou classes
//@NoArgsConstructor: gerar um construtor vazio
//@AllArgsConstructor: gerar um construtor com todos os parâmetros
//@Builder: gerar um builder (classe interna que auxilia na construção de objetos) para a classe
//@Data: gerar getters, setters, construtor para os campos necessários, e métodos equals e hashCode