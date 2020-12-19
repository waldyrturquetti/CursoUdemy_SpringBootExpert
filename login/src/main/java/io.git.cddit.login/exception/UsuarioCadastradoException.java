package io.git.cddit.login.exception;

public class UsuarioCadastradoException extends RuntimeException {

    public UsuarioCadastradoException(String login ){

        super("Usuário já cadastrado para o login " + login);
        //Quando a classe UsuarioCadastradoException é instanciada é feito o super que extende da
        // classe RuntimeException que é lançado um erro de exceção com essa mensagem.
    }
}

//super
//O super() serve para chamar o construtor da superclasse. Ele sempre é chamado, mesmo quando não está explícito no código,
// quando for explicitado deve ser o primeiro item dentro do construtor.

//RuntimeException
//RuntimeException deve ser usada quando a exceção pode ser prevenida. Use ela, indiretamente, quando quer sinalizar para o programador
// usuário do seu código que ele pode tentar resolver o problema quando esta exceção for lançada. Por causa disto ela é considerada uma exceção
// não checada.
//
//Elas são muito usadas em erros de programação que só podem ser verificados em tempo de execução, por isto não é obrigado capturar. Erro
// de programação deve deixar a aplicação quebrar. Eles só ocorrem porque não foram prevenidos antes de acontecer, por isto são chamados
// erros de programação. Uma verificação antes do erro ocorrer resolverá o problema. Exemplos de erros
// deste tipo são ArrayIndexOutOfBoundsException e NullPointerException.
//
//Nada impede de você criar suas próprias derivadas desta classe. Você só não deve fazer isto porque não quer ter o trabalho
// de capturar depois. A filosofia do Java é que sempre que for possível fazer algo com o problema, uma exceção deve ser capturada,
// então este tipo de exceção deve ser evitada tanto quanto possível. O que não quer dizer que qualquer exceção deve ser capturada.
// Mesmo as exceções checadas muitas vezes o melhor é delegar para outro método. E as não checadas só devem ser capturadas se naquele
// contexto você consegue fazer algo para se recuperar do erro, mesmo que seja apresentar uma mensagem personalizada.
//
//Para erros que devem ser prevenidos usa-se a Exception que é mais genérica e envolve qualquer exceção, inclusive ela pode ser uma
// RuntimeException já que todas as exceções devem ser derivadas de Exception. Também é usada indiretamente. Ela é considerada um exceção
// checada. Ou seja, todas as exceções que não são RuntimeException são checadas. Exemplos são todas derivadas e a própria
// IOException ou DataFormatException. Estas exceções ocorrem em situação que o programador não costuma poder controlar,
// depende do uso da aplicação e talvez só o usuário dela é que pode fazer alguma coisa.
//
//Quando uma exceção checada é usada, o código deve capturá-la ou usar throws (há exemplos de uso e mais informações nessa pergunta)
// no método para delegar sua captura. As exceções de runtime não precisam fazer isto, como o próprio nome indica elas não devem ser
// consideradas pelo compilador. Claro que cabe ao programador analisar a situação e decidir se aquele caso deve usar uma exceção checada
// ou não, quando é opcional. Há casos que mesmo um erro de runtime deveria ser obrigado a ter algum tratamento quando o seu fluxo depende
// daquilo correto e algo pode ser melhor tratado.
//
//É importante salientar que o uso destas classes é indireto porque o ideal é lançar exceções derivadas delas e não elas mesmas
// que são muito genéricas e idealmente até deveriam ser abstratas. Sempre deve-se lançar as exceções mais específicas possíveis.
//
//Uma exceção é representada por uma instância da classe Throwable(uma subclasse direta de Object) ou uma de suas subclasses.
//
//Throwablee todas as suas subclasses são, coletivamente, as classes de exceção .
//
//As classes Exceptione Errorsão subclasses diretas de Throwable:
//
//Exception é a superclasse de todas as exceções das quais os programas comuns podem desejar se recuperar.
//
//A classe RuntimeExceptioné uma subclasse direta de Exception. RuntimeExceptioné a superclasse de todas as exceções que podem ser lançadas por
// muitas razões durante a avaliação da expressão, mas das quais a recuperação ainda pode ser possível.
//
//RuntimeExceptione todas as suas subclasses são, coletivamente, as classes de exceção de tempo de execução .
//
//Error é a superclasse de todas as exceções das quais normalmente não se espera que os programas comuns se recuperem.
//
//Errore todas as suas subclasses são, coletivamente, as classes de erro .
//
//As classes de exceção não verificadas são as classes de exceção de tempo de execução e as classes de erro.
//
//As classes de exceção verificadas são todas as classes de exceção, exceto as classes de exceção não verificadas. Ou seja,
// as classes de exceção verificadas são Throwablee todas as suas subclasses exceto RuntimeExceptione suas subclasses e Errore suas subclasses.
//
//Os programas podem usar as classes de exceção pré-existentes da API da plataforma Java SE em throw instruções ou definir classes de exceção
// adicionais como subclasses de Throwable ou de qualquer uma de suas subclasses, conforme apropriado. Para tirar vantagem da verificação
// em tempo de compilação para manipuladores de exceção ( §11.2 ), é típico definir a maioria das novas classes de exceção como classes
// de exceção verificadas, ou seja, como subclasses de Exceptionque não são subclasses de RuntimeException.
//
//A classe Error é uma subclasse separada de Throwable, distinta da Exception hierarquia de classes, para permitir que os programas usem o
// idioma " } catch (Exception e) {"  para capturar todas as exceções das quais a recuperação pode ser possível sem detectar
// erros dos quais a recuperação normalmente não é possível .
