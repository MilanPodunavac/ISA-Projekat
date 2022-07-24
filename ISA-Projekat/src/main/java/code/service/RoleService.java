package code.service;

import code.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Role findById(Integer id);
    Role findByName(String name);
}
