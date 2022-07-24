package code.service;

import code.model.Role;

public interface RoleService {
    Role findByName(String name);
}
