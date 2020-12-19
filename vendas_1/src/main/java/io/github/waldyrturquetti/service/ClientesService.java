package io.github.waldyrturquetti.service;

import io.github.waldyrturquetti.model.Cliente;
import io.github.waldyrturquetti.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.PortUnreachableException;

@Service
public class ClientesService {

    //@Autowired //Com o @Autowired em cima do "private ClientesRepository repository", não é necessário o construtor
    private ClientesRepository repository;

    //@Autowired  //não é necessário, pois já temos o construtor
    public ClientesService(ClientesRepository repository){ //Construtor
        this.repository = repository;
    }

    public void salvarCliente(Cliente cliente){
       validarCliente(cliente);
       this.repository.persistir(cliente);
   }

    public void validarCliente(Cliente cliente){
       //aplica validacao
    }
}
