package io.github.waldyrturquetti.service.impl;

import io.github.waldyrturquetti.domain.entity.Cliente;
import io.github.waldyrturquetti.domain.entity.ItemPedido;
import io.github.waldyrturquetti.domain.entity.Pedido;
import io.github.waldyrturquetti.domain.entity.Produto;
import io.github.waldyrturquetti.domain.enums.StatusPedido;
import io.github.waldyrturquetti.domain.repository.Clientes;
import io.github.waldyrturquetti.domain.repository.ItemsPedido;
import io.github.waldyrturquetti.domain.repository.Pedidos;
import io.github.waldyrturquetti.domain.repository.Produtos;
import io.github.waldyrturquetti.exception.PedidoNaoEncontradoException;
import io.github.waldyrturquetti.exception.RegraNegocioException;
import io.github.waldyrturquetti.rest.dto.ItemPedidoDTO;
import io.github.waldyrturquetti.rest.dto.PedidoDTO;
import io.github.waldyrturquetti.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;           //Com o final não é necessário fazer o construtor,
    private final Clientes clientesRepository;  //POis ele obriga que estancie na criação do objeto
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;

//    public PedidoServiceImpl(Pedidos repository, Clientes clientes){
//        this.repository = repository;
//        this.clientesRepository = clientes;
//    }

    @Override
    @Transactional          //Garante que aconteça tudo, e se qualquer error ocorrer desfaz tudo que já tinha feito
    public Pedido salvar( PedidoDTO dto ) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException() );
    }


    public List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());



    }
}