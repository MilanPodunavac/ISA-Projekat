package code.service;

import code.model.Role;

import java.util.List;

public interface RoleService {
    Role findById(Integer id);
    List<Role> findByName(String name);
}
