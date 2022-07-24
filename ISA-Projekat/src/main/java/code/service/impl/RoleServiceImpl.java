package code.service.impl;

import code.model.Role;
import code.repository.RoleRepository;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = this.roleRepository.findByName(name);
        return role;
    }
}
