package io.git.cddit.login.rest;

import io.git.cddit.login.exception.UsuarioCadastradoException;
import io.git.cddit.login.model.entity.Usuario;
import io.git.cddit.login.model.repository.ClienteRepository;
import io.git.cddit.login.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

//@Controller
//Associada com classes que possuem métodos que processam requests numa aplicação web.
//A anotação @RestController permite definir um controller com características REST;
@RestController

//A anotação @RequestMapping permite definir uma rota. Caso não seja informado o método HTTP da rota,
//ela será definida para todos os métodos.
//Geralmente utilizada em cima dos métodos de uma classe anotada com @Controller. Serve para você colocar os
//endereços da sua aplicação que, quando acessados por algum cliente, deverão ser direcionados
// para o determinado método.
@RequestMapping("/api/usuarios")

//Com o Lombok , é possível gerar um construtor para todos os campos da classe (com @AllArgsConstructor )
// ou todos os campos finais da classe (com @RequiredArgsConstructor ). Além disso, se você ainda precisa
// de um construtor vazio, pode acrescentar uma anotação @NoArgsConstructor adicional .
//@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    //@PostMapping é uma maneira mais simples de implementar URL handler da anotação @RequestMapping
    // com o método Post. Na implementação de @PostMapping ele é anotado com @RequestMapping especificando
    // o método Post,
    @PostMapping

    //Se quisermos especificar o status de resposta de sucesso de um método controlador , podemos marcar esse método
    // com @ResponseStatus. Ele possui dois argumentos intercambiáveis para o status de resposta desejado:
    // código e valor. Por exemplo, podemos indicar que o servidor se recusa a preparar café porque é um bule :
    //@ResponseStatus(HttpStatus.BAD_REQUEST, reason = "Some parameters are invalid")
    //Quando um endpoint retorna com sucesso, o Spring fornece uma resposta HTTP 200 (OK).
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid Usuario usuario){
        //@ResponseBody
        //Utilizada em métodos anotados com @RequestMapping para indicar que o retorno
        // do método deve ser automaticamente escrito na resposta para o cliente.
        // Muito comum quando queremos retornar JSON ou XML em função de algum objeto da aplicação.
        //@Valid
        //Essa anotação serve para indicar que o objeto será validado tendo como base as anotações de
        // validação que atribuímos aos campos.

        try{
            //Chama do método salvar da classe UsuarioService
            service.salvar(usuario);
        }catch (UsuarioCadastradoException e){
            //Quando o erro de exceção do tipo UsuarioCadastradoException ocorrer, ou seja, RuntimeException
            // acontecer é lançado um exception que retorna o status do HTTP(nesse caso o BAD_REQUEST) e a msg
            // que do super da classe UsuarioCadastradoException
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, e.getMessage() );
        }
    }
}
