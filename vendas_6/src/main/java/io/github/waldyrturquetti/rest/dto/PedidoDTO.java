package io.github.waldyrturquetti.rest.dto;

import io.github.waldyrturquetti.domain.entity.ItemPedido;
import io.github.waldyrturquetti.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private Integer cliente;
    @NotNull(message = "{campo.total-pedido.obrigatorio}")
    private BigDecimal total;
    @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
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
