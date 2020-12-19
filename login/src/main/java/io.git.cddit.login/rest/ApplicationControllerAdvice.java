package io.git.cddit.login.rest;

import io.git.cddit.login.exception.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

//@RestControllerAdvice é apenas um facilitador para você não ter que colocar @ControllerAdvice + @ResponseBody.
// Porém o efeito de ambos é o mesmo
//@ResponseBody
//Simplificando, a anotação _ @ RequestBody_ mapeia o corpo HttpRequest para um objeto de transferência
// ou domínio, permitindo a desserialização automática do corpo HttpRequest de entrada em um objeto Java.
//O Spring desserializa automaticamente o JSON para um tipo Java, assumindo que um apropriado seja especificado.
// Por padrão, o tipo que anotamos com a anotação _ @ RequestBody_ deve corresponder ao JSON enviado do nosso
// controlador do lado do cliente
//A anotação _ @ ResponseBody_ informa a um controlador que o objeto retornado é serializado automaticamente no
// JSON e passado de volta ao objeto HttpResponse.
//@ControllerAdvice
//Ao usar o NotFoundException em muitos controllers,  todos devem ter também o mesmo método de tratamento da exceção.
// Para evitar essa duplicação de código, podemos criar uma classe separada e anotá-la com @ControllerAdvice.
// Dessa forma, os métodos anotados com @ExceptionHandler dessa classe serão invocados para as exceções lançadas
// por qualquer controller da aplicação

//Serialização é o processo de transformar algum objeto em um formato de dados que pode ser restaurado mais tarde.
// As pessoas geralmente serializam objetos para salvá-los no armazenamento ou para enviar como parte das comunicações.
// A desserialização é o inverso desse processo – tomando dados estruturados a partir de algum formato e
// reconstruindo-o em um objeto.

@RestControllerAdvice
public class ApplicationControllerAdvice {

    //O @ExceptionHandler é uma anotação usada para tratar as exceções específicas e enviar as respostas
    // personalizadas ao cliente.
    @ExceptionHandler(MethodArgumentNotValidException.class)

    // @ResponseStatus
    //Quando um endpoint retorna com sucesso, o Spring fornece uma resposta HTTP 200 (OK).
    //Se quisermos especificar o status de resposta de um método controlador , podemos marcar esse método com @ResponseStatus.
    // Ele possui dois argumentos intercambiáveis para o status de resposta desejado: código e valor.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErros(MethodArgumentNotValidException ex ){

        //MethodArgumentNotValidException
        // Exceção a ser lançada quando a validação de um argumento anotado com @Valid falha. Estende-se a BindException.


        //BindingResult
        //Interface geral que representa resultados de ligação. Estende a interface de recursos de registro de erro, permitindo que um
        // Validator seja aplicado e adiciona, análise específica de ligação e construção de modelo.
        //Serve como detentor de resultado para a DataBinder, obtido por meio do DataBinder.getBindingResult() método.
        // As implementações de BindingResult também podem ser usadas diretamente, por exemplo, para invocar um Validator
        // nele (por exemplo, como parte de um teste de unidade).
        //Lançado quando os erros de ligação são considerados fatais. Implementa a BindingResult interface (e sua super interface Errors)
        // para permitir a análise direta de erros de ligação.
        //A partir do Spring 2.0, esta é uma classe de propósito especial. Normalmente, o código do aplicativo funcionará com
        // a BindingResult interface ou com um DataBinder que, por sua vez, expõe um BindingResult via DataBinder.getBindingResult().
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(messages);
    }

    //O @ExceptionHandler é uma anotação usada para tratar as exceções específicas e enviar as respostas
    // personalizadas ao cliente.
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex){

        //ResponseStatusException
        //Classe base para exceções associadas a códigos de status de resposta HTTP específicos.

        String mensagemErro = ex.getReason();
        HttpStatus codigoStatus = ex.getStatus();
        ApiErrors apiErrors = new ApiErrors(mensagemErro);
        return new ResponseEntity(apiErrors, codigoStatus);
    }
}

//Temos três maneiras de usar @ResponseStatus para converter uma exceção em um status de resposta HTTP:
//
//usando @ExceptionHandler
//usando @ControllerAdvice
//marcando a classe Exception
//Para usar as duas primeiras soluções, temos que definir um método de tratamento de erros.
//Podemos usar @ResponseStatus com esses métodos de tratamento de erros da mesma forma que fizemos com os métodos MVC regulares na seção anterior.
//
//Quando não precisamos de respostas de erro dinâmicas, a solução mais direta é a terceira: marcar a classe
// Exception com @ResponseStatus
//
//Quando o Spring detecta essa exceção , ele usa as configurações que fornecemos em @ResponseStatus .
//Observe que quando marcamos uma classe Exception com @ResponseStatus , Spring sempre chama HttpServletResponse.sendError ()
// , quer definamos o motivo ou não.
//Observe também que o Spring usa a mesma configuração para subclasses, a menos que as marcemos com @ResponseStatus também.

//Collection
//Esta classe consiste exclusivamente em métodos estáticos que operam ou retornam coleções. Ele contém algoritmos polimórficos que
// operam em coleções, "invólucros", que retornam uma nova coleção apoiada por uma coleção especificada e algumas outras probabilidades
// e extremidades.
//Todos os métodos dessa classe lançam uma NullPointerException se as coleções ou objetos de classe fornecidos a eles forem nulos.
//
//A documentação para os algoritmos polimórficos contidos nesta classe geralmente inclui uma breve descrição da implementação.
// Essas descrições devem ser consideradas como notas de implementação, ao invés de partes da especificação. Os implementadores devem
// se sentir à vontade para substituir outros algoritmos, desde que a especificação em si seja cumprida.
// (Por exemplo, o algoritmo usado por sort não precisa ser um mergesort, mas precisa ser estável.)
//
//Os algoritmos "destrutivos" contidos nesta classe, ou seja, os algoritmos que modificam a coleção na qual operam,
// são especificados para lançar UnsupportedOperationException se a coleção não suportar a (s) primitiva (s) de mutação
// apropriada (s), como o método set. Esses algoritmos podem, mas não são obrigados a, lançar essa exceção se uma chamada não
// tiver efeito sobre a coleção. Por exemplo, chamar o método de classificação em uma lista não modificável que
// já está classificada pode ou não lançar UnsupportedOperationException.
// Coleções são usadas para armazenar, recuperar e manipular dados, assim como facilitar a transmissão de dados de um método para outro.
//Os três grupos do Java Collections são:
//Listas – Listas de itens, gerencia um grupo de elementos em sequência,como se fosse uma matriz, mas, a grande diferença é que List a quantidade de elementos é modificada dinamicamente. Classes que implementam java.util.List;
//Conjuntos - Itens exclusivos, não permitem duplicação de elementos, classes que implementam java.util.Set;
//Mapas – Itens com uma identificação exclusivaClasses que implementam java.util.Map.

//strem
//O stream é um objeto de transmissão de dados. Onde um fluxo de dados serial é feito através de uma origem e de um destino.
//Para manipular objetos serializados são usados dois tipos de stream o FileInputStream e o FileOutputStream.
//Conversões:
//• List para stream: .stream()
//• Stream para List: .collect(Collectors.toList())
//
//Strem é uma sequencia de elementos advinda de uma fonte de dados que
//oferece suporte a "operações agregadas".
//• Fonte de dados: coleção, array, função de iteração, recurso de E/S
//Características
//• Stream é uma solução para processar sequências de dados de forma:
//• Declarativa (iteração interna: escondida do programador)
//• Parallel-friendly (imutável -> thread safe)
//• Sem efeitos colaterais
//• Sob demanda (lazy evaluation)
//• Acesso sequencial (não há índices)
//• Single-use: só pode ser "usada" uma vez
//• Pipeline: operações em streams retornam novas streams. Então é possível
//criar uma cadeia de operações (fluxo de processamento).

//maps
//A função "map" (não confunda com a estrutura de dados Map) é uma
//função que aplica uma função a todos elementos de uma stream.

//Então. O objeto errors do método validate(..) é o registrador dos erros de validação e conversão do Spring. Em resumo,
// como a validação é aplicada automaticamente pelo Spring (quando você anota com @Valid) ele vai usar sua implementação de
// Validator, onde você vai registrando as ocorrências (rejeitando valores através do objeto errors).
//Como a validação ocorre durante o processo de binding (quando seu objeto está sendo montado pelo Spring com os dados
// que vieram do formulário), você não teria meios de saber qual o resultado do processo. A menos que o Spring te desse acesso
// aos resultados, que é o que ele faz permitindo que você receba um objeto de BindingResult no método. Com ele você pode verificar
// se houve algum problema e prever uma volta para o formulário, por exemplo. Sobre os métodos:
//errors.rejectValue("paginas", "field.required");
//Aqui temos a maneira padrão de indicar problemas de validação quanto a um campo. Dada um verificação sua, se algo deu errado com o valor passado no campo paginas, você rejeita o valor, registrando no objeto errors.
//
//Vejamos o outro exemplo:
//ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
//Aqui já temos uma classe de Utils (utilitária) apenas pra facilitar sua escrita. Como o próprio nome do método ja diz caso o valor
// do campo titulo esteja vazio seu valor será rejeitado, e um erro de validação será adicionado. Internamente ocorre o mesmo
// errors.rejectValue("titulo", "field.required"); citado anteriormente. Ou seja, a classe só te ajuda a não escrever tanto if
// pra String vazia, que é algo super comum.
//
//Sobre o parâmetro "field.required", ele representa a chave usada pra registrar mensagens de erro relativas a ausência do valor.
// Poderia ser usado qualquer outra String como chave, mas "field.required" acaba sendo um padrão pra campos requeridos. Tanto que o
// próprio framework já é capaz de disponibilizar mensagens de erro padrão pra essa chave, quando utilizada.