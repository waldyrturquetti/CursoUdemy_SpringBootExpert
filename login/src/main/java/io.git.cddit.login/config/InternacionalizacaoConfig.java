package io.git.cddit.login.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

//@Configuration, que é utilizada quando queremos indicar que uma classe contém métodos que produzem beans.
// Caso fique mais curioso, vá na classe mãe e veja os métodos anotados com @Bean.
@Configuration
public class InternacionalizacaoConfig {

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");    //Aqui, é importante fornecer o nome de base, pois os nomes de
                                                            // arquivo específicos do local serão resolvidos com base no nome fornecido.
        messageSource.setDefaultEncoding("ISO-8859-1");
        messageSource.setDefaultLocale( Locale.getDefault() );
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}

//LocalValidatorFactoryBean
//LocalValidatorFactoryBean é baseado no padrão do adaptador que unifica o Validatore Validador de interface de validação
// de bean JSR 349/303. Esta classe também implementa ValidatorFactory, o que significa que ele também cuida da inicialização do JSR 349
//Para usar mensagens de nome personalizadas em um arquivo de propriedades, como precisamos definir um LocalValidatorFactoryBean
// e registrar a messageSource:

//MessageSource
//MessageSource é um recurso poderoso disponível em aplicativos Spring. Isso ajuda os desenvolvedores de aplicativos a lidar com
// vários cenários complexos com a escrita de muito código extra, como configuração específica do ambiente,
// internacionalização ou valores configuráveis.
//
//Um contexto de aplicativo delega a resolução da mensagem a um bean com o nome exato messageSource.
//
//ReloadableResourceBundleMessageSource é a implementação de MessageSource mais comum que resolve mensagens de pacotes de recursos para
// diferentes localidades
