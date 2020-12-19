package io.github.waldyrturquetti.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrendenciaisDTO {
    private String login;
    private String senha;
}