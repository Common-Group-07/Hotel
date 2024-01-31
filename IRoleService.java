package com.hotelbooking.service;

import java.util.List;

import javax.management.relation.Role;

import org.apache.catalina.User;

public interface IRoleService {
	
	List<Role> getRoles();
	
	Role createRole(Role theRole);
	
	void deleteRole(Long id);
	
	Role findByName(String name);
	
	User removeUserFromRole(Long userId, Long roleId);
	
	User assignRoleToUser(Long userId, Long roleId);
	
	Role removeAllUsersFromRole(Long roleId);
	
	

}
