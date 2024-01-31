package com.hotelbooking.service;

import java.util.List;
import java.util.Optional;

import javax.management.relation.Role;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class RoleService implements IRoleService {
	
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	@Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role createRole(Role theRole) {
	    String roleName= "ROLE_" + theRole.getName().toUppperCase();
	    Role role= new Role(roleName);
	    if(roleRepository.existsByName(role)) {
	    	throw new RoleAlreadyExistException(theRole.getName()+ " role already exists");
	    }
	    
		return roleRespository.save(role);
	}

	@Override
	public void deleteRole(Long roleId) {
		this.removeUserFromRole(roleId);
		roleRepository.deleteById(roleId);
		
	}

	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name).get();
	}

	@Override
	public User removeUserFromRole(String userId, Long roleId) {
		Optional<User> user= userRepository.findById(userId);
		Optional<Role> role= roleRepository.findById(roleId);
		if(role.isPresent() && role.get().getUsers().contains(user.get()) {
			role.get().removeUserFromRole(user.get());
			roleRepository.save(role.get());
			return user.get();
			
		}
		throw new usernameNotFoundException("User not found");
	}

	@Override
	public User assignRoleToUser(Long userId, Long roleId) {
		Optional<User> user= userRepository.findById(userId);
		Optional<Role> role= roleRepository.findById(roleId);
		if(user.isPresent() && user.get().getRoles().contains(role.get())) {
			throw new UserAlreadyExistException(
					user.get().getFirstName()+ " is already assigned to the"+ role.get().getName()+" role");
		}
		if(role.isPresent()) {
			role.get().assignRoleToUser(user.get());
			roleRepository.save(role.get());
		}
		return user.get();
	}

	@Override
	public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.get().removeAllUsersFromRole();
        
		return roleRepository.save(role.get());
	}

}
