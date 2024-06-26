package tbd.group3.control2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tbd.group3.control2.controllers.DTO.AuthLoginDTO;
import tbd.group3.control2.controllers.DTO.AuthRegisterDTO;
import tbd.group3.control2.controllers.DTO.AuthResponse;
import tbd.group3.control2.entities.RolEntity;
import tbd.group3.control2.entities.UsuarioEntity;
import tbd.group3.control2.repositories.PermisosRepository;
import tbd.group3.control2.repositories.RolRepository;
import tbd.group3.control2.repositories.UsuarioRepository;
import tbd.group3.control2.utils.JwtUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisosRepository permisosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuarioEntity = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //usuarioEntity.getRoles()
         //       .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        List<RolEntity> rolesByUser = rolRepository.findAllByUsuario(usuarioEntity.getId());
        rolesByUser
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getNombre().name()))));
        rolesByUser.stream()
                .flatMap( role -> permisosRepository.findAllByRol(role.getId()).stream())
                .forEach( permiso ->  authorityList.add(new SimpleGrantedAuthority(permiso.getNombre().name())));

        //usuarioEntity.getRoles().stream()
        //        .flatMap(role -> role.getPermissionList().stream())
        //        .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));



        return new User(usuarioEntity.getUsername(), usuarioEntity.getPassword(),
                usuarioEntity.isEnabled()   , usuarioEntity.isAccountNoExpired(),
                usuarioEntity.isCredentialNoExpired(), usuarioEntity.isAccountNoLocked(),
                authorityList);
    }

    public AuthResponse loginUser(AuthLoginDTO authLoginDTO){

        String username = authLoginDTO.username();
        String password = authLoginDTO.password();

        Authentication authentication = this.authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.CreateToken(authentication);

        return new AuthResponse(username,"User logged successfull",accessToken,true);
    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthRegisterDTO authRegisterDTO){
        String username = authRegisterDTO.username();
        String password = authRegisterDTO.password();
        List<String> roleRequest = authRegisterDTO.roleRequest().roleListName();
        Set<RolEntity> rolEntities = new HashSet<>(rolRepository.findRolEntitiesByRoleEnumIn(roleRequest));
        if(rolEntities.isEmpty()){
            throw new IllegalArgumentException("The roles specified does not exist.");
        }

        UsuarioEntity usuario = UsuarioEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
         //       .roles(rolEntities)
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .accountNoLocked(true)
                .build();

        UsuarioEntity usuarioCreado = usuarioRepository.create(usuario,username);

        rolEntities.forEach( rolEntity -> rolRepository.createByUser(rolEntity, usuarioCreado.getId()) );

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        List<RolEntity> rolesByUser = rolRepository.findAllByUsuario(usuarioCreado.getId());
        rolesByUser
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getNombre().name()))));
        rolesByUser.stream()
                .flatMap( role -> permisosRepository.findAllByRol(role.getId()).stream())
                .forEach( permiso ->  authorityList.add(new SimpleGrantedAuthority(permiso.getNombre().name())));

        // usuarioCreado.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        //usuarioCreado.getRoles().stream()
        //        .flatMap(role -> role.getPermissionList().stream())
        //        .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioCreado.getUsername(), null, authorityList);

        String accessToken = jwtUtils.CreateToken(authentication);

        return new AuthResponse(usuarioCreado.getUsername(), "User created successfully", accessToken, true);
    }
}
