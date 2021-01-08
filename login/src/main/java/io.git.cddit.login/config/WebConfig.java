package io.git.cddit.login.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

//@Configuration:
// Marca a classe como uma fonte de definições de bean para o contexto do aplicativo.
//Ou seja toda fez que for usar a @bean injetar o @Configuration
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean(){

        List<String> all = Arrays.asList("*");

        CorsConfiguration corsConfiguration = new CorsConfiguration();
            //CorsConfiguration nos permite especificar como as requisições CORS devem ser processadas: origens,
            // cabeçalhos e métodos permitidos, entre outros.
            //
            //Um contêiner para configuração de CORS junto com métodos para verificar a origem real, métodos HTTP
            // e cabeçalhos de uma determinada solicitação.

        corsConfiguration.setAllowedOrigins(all);
            //Uma lista de origens para as quais solicitações de origem cruzada são permitidas.

        corsConfiguration.setAllowedHeaders(all);
            //Definir a lista de outros que cabeçalhos simples cabeçalhos de resposta (ou seja Cache-Control, Content-Language,
            // Content-Type, Expires, Last-Modified, ou Pragma) que uma resposta real pode ter e pode ser exposta.

        corsConfiguration.setAllowedMethods(all);
            //Defina os métodos HTTP permitidos

        corsConfiguration.setAllowCredentials(true);
            //Se as credenciais do usuário são suportadas.


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            //CorsConfigurationSourceque usa padrões de caminho de URL para selecionar o CorsConfigurationpara uma solicitação.
            //A correspondência de padrões pode ser feita com a PathMatcherou com PathPatterns pré-analisados . A sintaxe é basicamente
            // a mesma, sendo a última mais adaptada para uso da web e mais eficiente. A escolha depende da presença de resolvedString lookupPath
            // ou um com fallback, mas o fallback pode ser desabilitado.

        source.registerCorsConfiguration("/**", corsConfiguration);
            //Variante de setCorsConfigurations(Map)para registrar um mapeamento por vez.
            //public void registerCorsConfiguration(String pattern, CorsConfiguration config)

        CorsFilter corsFilter = new CorsFilter(source);
            //Filter para lidar com solicitações de pré-voo do CORS e interceptar solicitações simples e reais do CORS com um CorsProcessor e para
            // atualizar a resposta, por exemplo, com cabeçalhos de resposta do CORS, com base na política combinada por meio do fornecido
            // CorsConfigurationSource.
            //Esta é uma alternativa para configurar o CORS na configuração do Spring MVC Java e no namespace Spring MVC XML. É útil para aplicativos
            // que dependem apenas de spring-web (não de spring-webmvc) ou para restrições de segurança que requerem que verificações de CORS
            // sejam realizadas em Filternível.
            //Este filtro pode ser usado em conjunto DelegatingFilterProxy para ajudar na sua inicialização.

        FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<>(corsFilter);
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE); //Maior Precedencia

        return filter;
    }
}
//FilterRegistrationBean
//Se você deseja configurar um filtro de terceiros, você pode usar FilterRegistrationBean.
//
//Se o mapeamento baseado em convenção não for flexível o suficiente, podemos usar FilterRegistrationBean para o controle completo
// do aplicativo. Aqui, não use a anotação @Component para a classe de filtro, mas registre o filtro usando um FilterRegistrationBean .
//
//Não há uma anotação especial para denotar um filtro de servlet. Você acabou de declarar um @Beando tipo Filter(ou FilterRegistrationBean).
// Um exemplo (adicionar um cabeçalho personalizado a todas as respostas) está no EndpointWebMvcAutoConfiguration do próprio Boot ;
//
//Se você apenas declarar um, Filterele será aplicado a todas as solicitações. Se você também adicionar um, FilterRegistrationBean poderá
// especificar servlets individuais e padrões de url a serem aplicados.
//
//Nota:
//A partir do Spring Boot 1.4, FilterRegistrationBeannão está obsoleto e simplesmente mudou os pacotes de
// org.springframework.boot.context.embedded.FilterRegistrationBeanparaorg.springframework.boot.web.servlet.FilterRegistrationBean

//CorsConfiguration
//As solicitações CORS são despachadas automaticamente para os vários HandlerMappings registrados . Eles lidam com solicitações de comprovação
// de CORS e interceptam solicitações simples e reais de CORS usando uma implementação CorsProcessor ( DefaultCorsProcessor por padrão)
// para adicionar os cabeçalhos de resposta CORS relevantes (como Access-Control-Allow-Origin ).
//
//CorsConfiguration nos permite especificar como as requisições CORS devem ser processadas: origens, cabeçalhos e métodos permitidos,
// entre outros. Podemos fornecê-lo de várias maneiras:
//
//AbstractHandlerMapping # setCorsConfiguration () permite especificar um Mapa com vários CorsConfiguration s mapeados em padrões de caminho,
// como / api / **
//As subclasses podem fornecer seu próprio CorsConfiguration substituindo o método AbstractHandlerMapping # getCorsConfiguration
// (Object, HttpServletRequest)
//Os manipuladores podem implementar a interface CorsConfigurationSource (como ResourceHttpRequestHandler agora faz)
// para fornecer um CorsConfiguration para cada solicitação
//
//Por padrão, um recém-criado CorsConfigurationnão permite nenhuma solicitação de origem cruzada e deve ser configurado explicitamente
// para indicar o que deve ser permitido. Use applyPermitDefaultValues() para inverter o modelo de inicialização para começar com padrões
// abertos que permitem todas as solicitações de origem cruzada para solicitações GET, HEAD e POST.
