package tbd.group3.control2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tbd.group3.control2.entities.*;
import tbd.group3.control2.repositories.PermisosRepository;
import tbd.group3.control2.repositories.RolRepository;
import tbd.group3.control2.repositories.UsuarioRepository;

import java.util.Set;

@SpringBootApplication
public class Control2Application {

	public static void main(String[] args) {
		SpringApplication.run(Control2Application.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, PermisosRepository permisosRepository, RolRepository rolRepository) {
        return args -> {
			/* Create PERMISSIONS */
			PermisosEntity permisoCrear = permisosRepository.create(  PermisosEntity.builder()
					.nombre(EPermisos.CREATE)
					.build(), null);

			PermisosEntity permisoLeer = permisosRepository.create(  PermisosEntity.builder()
					.nombre(EPermisos.READ)
					.build(),null);

			PermisosEntity permisoUpdate = permisosRepository.create(  PermisosEntity.builder()
					.nombre(EPermisos.UPDATE)
					.build(),null);

			PermisosEntity permisoBorrar = permisosRepository.create(  PermisosEntity.builder()
					.nombre(EPermisos.DELETE)
					.build(),null);

			PermisosEntity permisoRefactor = permisosRepository.create(  PermisosEntity.builder()
					.nombre(EPermisos.REFACTOR)
					.build(),null);

			/* Create ROLES */
			Long id_roleAdmin = rolRepository.create(RolEntity.builder()
					.nombre(ERoles.ADMIN)
					.build(), null).getId();

			permisosRepository.createPermisoByRol(permisoCrear, id_roleAdmin);
			permisosRepository.createPermisoByRol(permisoLeer, id_roleAdmin);
			permisosRepository.createPermisoByRol(permisoUpdate, id_roleAdmin);
			permisosRepository.createPermisoByRol(permisoBorrar, id_roleAdmin);

			Long id_roleUser = rolRepository.create(RolEntity.builder()
					.nombre(ERoles.USER)
					.build(), null).getId();

			permisosRepository.createPermisoByRol(permisoCrear, id_roleUser);
			permisosRepository.createPermisoByRol(permisoLeer, id_roleUser);


			Long id_roleInvited = rolRepository.create(RolEntity.builder()
					.nombre(ERoles.INVITED)
					.build(), null).getId();
			permisosRepository.createPermisoByRol(permisoLeer, id_roleInvited);

			Long id_roleDeveloper = rolRepository.create(RolEntity.builder()
					.nombre(ERoles.DEVELOPER)
					.build(), null).getId();

			permisosRepository.createPermisoByRol(permisoCrear, id_roleDeveloper);
			permisosRepository.createPermisoByRol(permisoLeer, id_roleDeveloper);
			permisosRepository.createPermisoByRol(permisoUpdate, id_roleDeveloper);
			permisosRepository.createPermisoByRol(permisoRefactor, id_roleDeveloper);
			permisosRepository.createPermisoByRol(permisoBorrar, id_roleDeveloper);

			/* CREATE USERS */
			UsuarioEntity userSantiago = UsuarioEntity.builder()
					.username("santiago")
					.password(new BCryptPasswordEncoder().encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					//.roles(Set.of(roleAdmin))
					.build();
			try {
				rolRepository.createByUser(rolRepository.findById(id_roleAdmin), usuarioRepository.create(userSantiago, null).getId());
			}catch (Exception e) {
				System.out.println(e.getMessage());
			};

			UsuarioEntity userDaniel = UsuarioEntity.builder()
					.username("daniel")
					.password(new BCryptPasswordEncoder().encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					//.roles(Set.of(roleUser))
					.build();

			try{
			rolRepository.createByUser(rolRepository.findById(id_roleUser),usuarioRepository.create(userDaniel,null).getId());
			}catch (Exception e) {
				System.out.println(e.getMessage());
			};
			UsuarioEntity userAndrea = UsuarioEntity.builder()
					.username("andrea")
					.password(new BCryptPasswordEncoder().encode("321"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					//.roles(Set.of(roleInvited))
					.build();

			try{
			rolRepository.createByUser(rolRepository.findById(id_roleInvited),usuarioRepository.create(userAndrea,null).getId());
			}catch (Exception e) {
				System.out.println(e.getMessage());
			};

			UsuarioEntity userAnyi = UsuarioEntity.builder()
					.username("anyi")
					.password(new BCryptPasswordEncoder().encode("aaaa"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					//.roles(Set.of(roleInvited))
					.build();

			try{
			rolRepository.createByUser(rolRepository.findById(id_roleInvited),usuarioRepository.create(userAnyi,null).getId());
			}catch (Exception e) {
				System.out.println(e.getMessage());
			};
			// userRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi));
		};
	}
}
