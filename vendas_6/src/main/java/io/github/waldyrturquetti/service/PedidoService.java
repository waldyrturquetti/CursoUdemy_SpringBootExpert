package io.github.waldyrturquetti.service;

import io.github.waldyrturquetti.domain.entity.Pedido;
import io.github.waldyrturquetti.domain.enums.StatusPedido;
import io.github.waldyrturquetti.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar( PedidoDTO dto );

    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
