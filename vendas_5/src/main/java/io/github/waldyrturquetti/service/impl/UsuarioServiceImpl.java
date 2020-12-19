package io.github.waldyrturquetti.service.impl;

import io.github.waldyrturquetti.domain.entity.Usuario;
import io.github.waldyrturquetti.domain.repository.UsuarioRepository;
import io.github.waldyrturquetti.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    public Usuario salvar(Usuario usuario){
        return repository.save(usuario);
    }

    public UserDetails autenticar( Usuario usuario ){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = encoder.matches( usuario.getSenha(), user.getPassword() );

        if(senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        if(!username.equals("walter")){
//            throw new UsernameNotFoundException("Usuario não encontrado na base.");
//        }
//
//        return User                       //Usuario em memoria.
//                .builder()
//                .username("walter")
//                .password(encoder.encode("123"))
//                .roles("USER","ADMIN")
//                .build();

        Usuario usuario = repository.findByLogin(username)
                .orElseThrow( () -> new UsernameNotFoundException("Usuario não encontrado na base de dados"));

        String[] roles = usuario.isAdmin() ?
                new String[]{"ADIM", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
   }
}
