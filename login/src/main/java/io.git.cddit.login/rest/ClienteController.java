package io.git.cddit.login.rest;

import io.git.cddit.login.model.entity.Cliente;
import io.git.cddit.login.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

//@RestController é uma anotação de conveniência para a criação de controladores Restful. É uma especialização de @Componente
// é autodetectada por meio da varredura de caminho de classe. Ele adiciona as anotações @Controllere @ResponseBody. Ele converte a
// resposta em JSON ou XML. Ele não funciona com a tecnologia de visualização, portanto, os métodos não podem retornar ModelAndView.
// É normalmente usado em combinação com métodos de manipulador anotados com base na @RequestMapping anotação.
@RestController

//Anotação para mapear solicitações da web em métodos em classes de tratamento de solicitações com assinaturas de método flexíveis.
//Tanto Spring MVC quanto Spring WebFlux suportam essa anotação por meio de um RequestMappingHandlerMappinge RequestMappingHandlerAdapter
// em seus respectivos módulos e estrutura de pacote. Para obter a lista exata de argumentos de método de manipulador suportados e tipos
// de retorno em cada um, use os links de documentação de referência abaixo:
//
//Argumentos e valores de retorno do método Spring MVC
//Argumentos e valores de retorno do método Spring WebFlux
//Observação: essa anotação pode ser usada tanto na classe quanto no método. Na maioria dos casos, ao método de aplicações de
// nível vai preferir usar um dos específico variantes método HTTP @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, ou @PatchMapping.
//
//NOTA: Ao usar interfaces de controlador (por exemplo, para proxy AOP), certifique-se de colocar consistentemente todas as suas anotações
// de mapeamento - como @RequestMappinge @SessionAttributes- na interface do controlador em vez de na classe de implementação.
@RequestMapping("/api/clientes")


//Spring MVC fornece anotações @CrossOrigin. Esta anotação marca o método
// ou tipo anotado como permitindo solicitações de origem cruzada.
//
//Por padrão, @CrossOrigin permite todas as origens, todos os cabeçalhos, os métodos HTTP especificados na anotação @RequestMapping
// e um maxAgede 30 minutos.
//@CrossOrigin("http://localhost:4200")          //É um mecanismo para permitir que o app Web seja executado em domínio distintos.
                                                //Exemplo:localhost:8080(SpringBoot) com localhost:4200(Angular)
public class ClienteController {

    private final ClienteRepository repository;

    @Autowired
    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping //Indica que vai retornar todos os clintes salvos no banco
    public List<Cliente> obterTodos(){
        return repository.findAll();
    }

    //@PostMapping é uma maneira mais simples de implementar URL handler da anotação @RequestMapping
    //com o método Post. Na implementação de @PostMapping ele é anotado com @RequestMapping especificando
    //o método Post,
    @PostMapping            //Indica que vai criar um recurso no servidor

    //Se quisermos especificar o status de resposta de sucesso de um método controlador , podemos marcar esse método
    // com @ResponseStatus. Ele possui dois argumentos intercambiáveis para o status de resposta desejado:
    // código e valor. Por exemplo, podemos indicar que o servidor se recusa a preparar café porque é um bule :
    //@ResponseStatus(HttpStatus.BAD_REQUEST, reason = "Some parameters are invalid")
    //Quando um endpoint retorna com sucesso, o Spring fornece uma resposta HTTP 200 (OK).
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar( @RequestBody @Valid Cliente cliente ){
        return repository.save(cliente);
    }

    @GetMapping("{id}")     //Indica que vai procurar/pesquisar um objeto no servidor, nesse caso atráves de um id
    public Cliente acharPorId( @PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }

    @DeleteMapping("{id}")  //Indica que vai excluir o objeto de id informado no servidor
    @ResponseStatus(HttpStatus.NO_CONTENT) //O NO_CONTENT informa que não há objeto de retorno.
    public void deletar( @PathVariable Integer id ){
//        repository.deleteById(id); //Funcionaria também dessa maneira.

        repository
            .findById(id)
            .map( cliente -> {
                repository.delete(cliente);
                return Void.TYPE;
            })
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id, @RequestBody @Valid Cliente clienteAtualizado ) {
        repository
                .findById(id)
                .map( cliente -> {
                    cliente.setNome((String) clienteAtualizado.getNome());
                    cliente.setCpf((String) clienteAtualizado.getCpf());
                    return repository.save(cliente);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }
}

//Request body
//O Request Body, ou corpo da requisição, é onde geralmente enviamos dados que queremos gravar no servidor.
//Não é muito utilizado em requisições do tipo GET, mas sim nas do tipo POST e PUT.
//É no corpo da requisição onde você envia dados de um formulário de cadastro em seu site, por exemplo.

//@Valid
//Quando o Spring Boot encontra um argumento anotado com @Valid , ele inicializa automaticamente a implementação JSR 380
// padrão - Hibernate Validator - e valida o argumento.

//@PathVariable
//Simplificando, a anotação @PathVariable pode ser usada para manipular variáveis de modelo no mapeamento de URI de solicitação
// e usá-las como parâmetros de método.
