package io.github.waldyrturquetti.rest.controler;

import io.github.waldyrturquetti.domain.entity.Cliente;
import io.github.waldyrturquetti.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

//@Controller       //Não vem com o ResponseBody
@RestController     //Já vem com o ResponseBody
@RequestMapping("/api/clientes")
public class ClienteController {

    //Não é necessário se usarmos o @GetMapping
//    @RequestMapping(
//            value = {"/api/clientes/hello/{nome}", "/api/hello"},
//            /*method = RequestMethod.GET,*/
//            method = RequestMethod.POST,
//            consumes = {"application/json", "aplication/xml"},
//            produces = {"application/json", "aplication/xml"}
//
//    )
//    @ResponseBody

    //Usando o GET no RequestMapping
//    public String helloCliente(@PathVariable("nome") String nomeCliente){
//        return String.format("Hello %s", nomeCliente);
//    }

    //Usando o POST no RequestMapping
//    public String helloCliente(@PathVariable("nome") String nomeCliente, @RequestBody Cliente cliente){
//        return String.format("Hello %s", nomeCliente);
//   }
    private Clientes clientes;

    public ClienteController(Clientes clientes){
        this.clientes = clientes;
    }

    //@GetMapping("/api/clientes/{id}") // Não é necessário quando usamos @RequestMapping("/api/clientes")
//    @GetMapping("/{id}")
//    //@ResponseBody
//    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id){
//        Optional<Cliente> cliente = clientes.findById(id);
//        if(cliente.isPresent()){
//            //Isso é equivalente a return ResponseEntity.ok(cliente.get()), ainda autorização e token
////            HttpHeaders headers = new HttpHeaders();
////            headers.put("Authorization", "token");
////            ResponseEntity<Cliente> responseEntity =
////                    new ResponseEntity<>(cliente.get(), headers, HttpStatus.OK);
//
//            return ResponseEntity.ok(cliente.get());
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    //@PostMapping("/api/clientes")
//    @PostMapping
////    @ResponseBody
//    public ResponseEntity save( @RequestBody Cliente cliente ){
//        Cliente clienteSalvo = clientes.save(cliente);
//        return ResponseEntity.ok(clienteSalvo);
//    }
////    @DeleteMapping("/api/clientes/{id}")
//    @DeleteMapping("/{id}")
////    @ResponseBody
//    public ResponseEntity delete(@PathVariable Integer id) {
//        Optional<Cliente> cliente = clientes.findById(id);
//
//        if (cliente.isPresent()) {
//            clientes.delete(cliente.get());
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    //@PutMapping("/api/clientes/{id}")
//    @PutMapping("/{id}")
////    @ResponseBody
//    public ResponseEntity update(@PathVariable Integer id,
//                                 @RequestBody Cliente cliente){
//
//        return clientes
//                .findById(id)
//                .map( clienteExistente -> {
//                    cliente.setId(clienteExistente.getId());
//                    clientes.save(cliente);
//                    return ResponseEntity.noContent().build();
//                }).orElseGet( () -> ResponseEntity.notFound().build() );
//    }
//
//    //@GetMapping("/api/clientes")
//    @GetMapping
//    public ResponseEntity find( Cliente filtro){
//
//        ExampleMatcher matcher = ExampleMatcher
//                                    .matching()
//                                    .withIgnoreCase()
//                                    .withStringMatcher(
//                                            ExampleMatcher.StringMatcher.CONTAINING);
//
//
//        Example example = Example.of(filtro,matcher);
//        List<Cliente> lista = clientes.findAll(example);
//        return ResponseEntity.ok(lista);
//    }


    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Integer id){
        return clientes
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND,
                            "Cliente não encontrado"));

        //if(cliente.isPresent()){
        //    return cliente.get();
        //}
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save( @RequestBody Cliente cliente ){
        return clientes.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clientes.findById(id)
                .map( cliente -> {
                    clientes.delete(cliente);
                    return cliente;
                })
                .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                                 @RequestBody Cliente cliente){

         clientes
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return clienteExistente;
                }).orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND,
                    "Cliente não encontrado"));
    }

    @GetMapping
    public List<Cliente> find( Cliente filtro){

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);


        Example example = Example.of(filtro,matcher);
        return clientes.findAll(example);
    }


}
