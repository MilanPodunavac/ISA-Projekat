package code.service.impl;

import code.model.Role;
import code.repository.RoleRepository;
import code.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository _roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this._roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        Role role = this._roleRepository.findByName(name);
        return role;
    }
}
