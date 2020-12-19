package io.github.waldyrturquetti.domain.repository;

import io.github.waldyrturquetti.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {}
