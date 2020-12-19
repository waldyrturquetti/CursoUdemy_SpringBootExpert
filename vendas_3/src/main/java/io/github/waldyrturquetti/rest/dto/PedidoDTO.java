package io.github.waldyrturquetti.rest.dto;

import io.github.waldyrturquetti.domain.entity.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;


/*  Exemplo de um objeto de um PedidoDTO
    {
        "cliente" : 1,
        "total" : 100,
        "itens" : [
            {
                "produto":1,
                "quantidade":10
            }
        ]
    }
*/

}
