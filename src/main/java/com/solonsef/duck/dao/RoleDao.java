package com.solonsef.duck.dao;

import com.solonsef.duck.entities.Role;

public interface RoleDao {

    public Role findRoleByName(String theRoleName);

}
