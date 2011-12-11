package javagie.security;

import java.util.ArrayList;
import java.util.List;

import javagie.entities.Usuario;
import javagie.services.FachadaService;
import javagie.util.EncodeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JavagieUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private Identidad identidad;
	
	@Autowired
	private FachadaService services;
	
	/**
	 * No hace nada
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authToken) throws AuthenticationException { }
	
	/**
	 * Proceso de identificacion del usuario 
	 * username: es un rut que identifica a un usuario interno mineduc ó establecimiento ó sostenedor
	 */
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authToken) throws AuthenticationException {
		String password = authToken.getCredentials().toString();
		Usuario usuario = null;
		
		if(username != null)
			usuario = services.traerUsuarioPorEmail(username);
		
		//verifica credenciales
		if (usuario == null || !usuario.getPassword().equals(EncodeUtil.generarHash("md5",password))) 
		{
			throw new AuthenticationServiceException("Usuario o contraseña equivocadas"); 
		}
		
		//paso identificacion correctamente
		
		//--------------------------------------------
        //CARGA ROLES SPRING SECURITY E IDENTIDAD
        //--------------------------------------------
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        if(usuario.getEsAdmin()) {
        	roles.add(new SimpleGrantedAuthority("ROL_ADMIN"));
        	identidad.setEsAdmin(true);
        }
        else {
        	roles.add(new SimpleGrantedAuthority("ROL_USER"));
        }
        
		identidad.setEmail(usuario.getEmail());
		identidad.setNombre(usuario.getNombres() + " " + usuario.getApellidos());
		identidad.setLoggedIn(true);
		
		return new User(identidad.getNombre(), usuario.getPassword(), roles);
	}

}
