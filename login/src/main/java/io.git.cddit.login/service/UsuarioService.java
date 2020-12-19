package io.git.cddit.login.service;

import io.git.cddit.login.exception.UsuarioCadastradoException;
import io.git.cddit.login.model.entity.Usuario;
import io.git.cddit.login.model.repository.UsuarioRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//Anotação que serve para definir uma classe como pertencente à camada de Serviço da aplicação.
//Associada com classes que representam a ideia do Service do Domain Driven Design. Para ficar menos
// teórico pense em classes que representam algum fluxo de negócio da sua aplicação.
// Por exemplo, um fluxo de finalização de compra envolve atualizar manipular o carrinho, enviar email,
// processar pagamento etc. Este é o típico código que temos dificuldade de saber onde vamos colocar,
// em geral ele pode ficar num Service :).
@Service

//implements UserDetailsService
//A interface UserDetailsService é usada para recuperar dados relacionados ao usuário.
// Ele possui um método denominado loadUserByUsername () que pode ser substituído para
// personalizar o processo de localização do usuário.
public class UsuarioService implements UserDetailsService {

    //@Autowired
    //A anotação @ Autowired fornece controle sobre onde e como a ligação entre os beans deve ser realizada.
    // Pode ser usado para em métodos setter, no construtor, em uma propriedade ou métodos com nomes arbitrários
    // e / ou vários argumentos.
    @Autowired
    private UsuarioRepository repository;

    public Usuario salvar(Usuario usuario){
        boolean exists = repository.existsByUsername(usuario.getUsername());
        //.existsByUsername é um método do repository UsuarioRepository que implemeta a interface JPA
        //que busca se existe uma linha na tabela em questão que tenha a informação
        // que colocamos, em alguma das colunas
        if (exists){
            throw new UsuarioCadastradoException(usuario.getUsername());
            //a classe UsuarioCadastradoException é um extends da classe RuntimeException.
            //que nesse caso envia erro de exceção se na hora de salavar o login do usuario,
            //esse login já existir na tebela usuarios.
        }
        return repository.save(usuario);//O .save é um método da interface JPA que faz um insert do objeto da
                                        //classe em questão no banco na tabela da entity
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {

        Usuario usuario = repository
                    //O .findByUsername(username) é bem parecido com existsByUsername(usuario.getUsername())
                    //só que ele procura por esse username e retorna o objeto(a linha) em questão se achar,
                    //se não achar retora um exception
                    .findByUsername(username)
                    //orElseThrow é ativado caso não ocorra de maneira esperada(exception) o método anterior,
                    //caso isso aconteça é instanciado o UsernameNotFoundException com a msg "Login não encontrado."
                    //tal exception serve para quando a implementação não puder localizar um User
                    // por seu nome de usuário.
                    .orElseThrow(() -> new UsernameNotFoundException("Login não encontrado.") );


        //User é um objeto do tipo Usuario
        return User                             //É equivalente ao usar o UserDetails como implementação
                                                // da classe Usuário

                //Definição do .builder() logo abaixo
                .builder()

                //set do username do objeto user
                .username(usuario.getUsername())

                //set do password do objeto user
                .password(usuario.getPassword())

                //difine que esse o objeto instanciado tenha o papel de user,
                //ou seja, não é um admin
                .roles("USER")

                //O método build () na classe Stream.Builder é usado para construir o fluxo.
                // Ele retorna o fluxo construído.
                .build()
                ;
    }
}

//Em programação orientada a objetos e funcional, um objeto imutável é um objeto no qual seu estado
// não pode ser modificado após ser criado. Ele é um contraste com um objeto mutável, que pode ser
// modificado após sua criação.


//Builder():
//A anotação @Builder produz APIs de construtor complexas para suas classes.
//
//@Builder permite que você produza automaticamente o código necessário para que sua classe seja instanciada
// com códigos como:
//Person.builder().name("Adam Savage").city("San Francisco").job("Mythbusters").job("Unchained Reaction").build();
//
//@Builder pode ser colocado em uma classe, ou em um construtor, ou em um método. Embora os modos "em uma classe" e "em um construtor"
// sejam os casos de uso mais comuns, @Builder é mais facilmente explicado com o caso de uso "método".
//
//Um método anotado com @Builder(a partir de agora chamado de destino ) faz com que as 7 coisas a seguir sejam geradas:
//
//Uma classe estática interna denominada FooBuilder, com os mesmos argumentos de tipo que o método estático (denominado construtor ).
//No construtor : Um campo não final privado não estático para cada parâmetro do destino .
//No construtor : Um construtor vazio de pacote privado no-args.
//No construtor : Um método do tipo 'setter' para cada parâmetro do destino : Tem o mesmo tipo desse parâmetro e o mesmo nome. Ele retorna o
// próprio construtor, para que as chamadas do configurador possam ser encadeadas, como no exemplo acima.
//No construtor : Um build() método que chama o método, passando em cada campo. Ele retorna o mesmo tipo que o destino retorna.
//No construtor : Uma toString() implementação sensata .
//Na classe que contém o destino : Um builder() método que cria uma nova instância do construtor .
//Cada elemento gerado listado será ignorado silenciosamente se esse elemento já existir (desconsiderando contagens de parâmetro
// e olhando apenas para nomes). Isso inclui o próprio construtor : Se essa classe já existir, o lombok simplesmente começará a
// injetar campos e métodos dentro dessa classe já existente, a menos que, é claro, os campos / métodos a serem injetados já existam.
// Você não pode colocar nenhum outro método (ou construtor) gerando anotações lombok em uma classe de construtor; por exemplo, você não pode
// colocar @EqualsAndHashCodena classe builder.
//@Builder pode gerar os chamados métodos 'singulares' para parâmetros / campos de coleção. Eles pegam 1 elemento em vez de uma lista inteira e
// adicionam o elemento à lista. Por exemplo: Person.builder().job("Mythbusters").job("Unchained Reaction").build();
// resultaria no List<String> jobscampo com 2 strings. Para obter esse comportamento, o campo / parâmetro precisa ser anotado com @Singular.
// O recurso possui sua própria documentação .
//
//Agora que o modo "método" está claro, colocar uma @Builderanotação nas funções do construtor de forma semelhante; efetivamente, os construtores
// são apenas métodos estáticos que têm uma sintaxe especial para invocá-los: seu 'tipo de retorno' é a classe que eles constroem, e seus parâmetros
// de tipo são iguais aos parâmetros de tipo da própria classe.
//
//Finalmente, aplicar @Buildera uma classe é como se você adicionasse @AllArgsConstructor(access = AccessLevel.PACKAGE) à classe e aplicasse
// a @Builder anotação a este construtor all-args. Isso só funciona se você não escreveu nenhum construtor explícito. Se você tiver um construtor
// explícito, coloque a @Builder anotação no construtor em vez de na classe. Note que se você colocar `@ Value` e` @ Builder` em uma classe,
// o construtor de pacote privado que `@ Builder` deseja gerar 'ganha' e suprime o construtor que` @ Value` deseja fazer.
//
//Se estiver usando @Builderpara gerar construtores para produzir instâncias de sua própria classe (este é sempre o caso,
// a menos que seja adicionado @Buildera um método que não retorna seu próprio tipo), você pode usar @Builder(toBuilder = true)para gerar
// também um método de instância em sua classe chamado toBuilder(); ele cria um novo construtor que começa com todos os valores
// desta instância. Você pode colocar a @Builder.ObtainViaanotação nos parâmetros (no caso de um construtor ou método) ou campos
// (no caso de @Builderem um tipo) para indicar meios alternativos pelos quais o valor para aquele campo / parâmetro é obtido a
// partir desta instância. Por exemplo, você pode especificar um método a ser invocado: @Builder.ObtainVia(method = "calculateFoo").
//
//O nome da classe builder é FoobarBuilder, onde Foobar é a forma simplificada, com capitalização do tipo de retorno do destino - isto é,
// o nome do seu tipo para @Builderconstrutores e tipos on, e o nome do tipo de retorno para @Buildermétodos on . Por exemplo, se @Builderfor
// aplicado a uma classe chamada com.yoyodyne.FancyList<T>, o nome do construtor será FancyListBuilder<T>. Se @Builderfor aplicado a um método
// que retorna void, o construtor será nomeado VoidBuilder.
//
//Os aspectos configuráveis do builder são:
//
//O nome da classe do construtor (padrão: tipo de retorno + 'Construtor')
//O nome do método build () (padrão "build":)
//O nome do método builder () (padrão "builder":)
//Se você quiser toBuilder()(padrão: não)
//O nível de acesso de todos os elementos gerados (padrão public:).
//(desanimado) Se você quiser que os métodos 'set' do seu construtor tenham um prefixo, ou seja
// , ao Person.builder().setName("Jane").build()invés de Person.builder().name("Jane").build()e o que deveria ser.
//Exemplo de uso em que todas as opções são alteradas de seus padrões:
//@Builder(builderClassName = "HelloWorldBuilder", buildMethodName = "execute", builderMethodName = "helloWorld", toBuilder = true,
// access = AccessLevel.PRIVATE, setterPrefix = "set")
//Quer usar seu construtor com Jackson , a ferramenta JSON / XML? Nós ajudamos você: Confira o recurso @Jacksonized .
//
//@ Builder.Default
//Se um determinado campo / parâmetro nunca for definido durante uma sessão de construção, ele sempre será 0 / null/ falso.
// Se você colocou @Builderuma classe (e não um método ou construtor), pode especificar o padrão diretamente no campo e anotar o campo
// com @Builder.Default:
//@Builder.Default private final long created = System.currentTimeMillis();