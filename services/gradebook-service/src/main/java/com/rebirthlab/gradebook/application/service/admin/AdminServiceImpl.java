package com.rebirthlab.gradebook.application.service.admin;

import com.rebirthlab.gradebook.application.service.user.AbstractUserServiceImpl;
import com.rebirthlab.gradebook.domain.model.user.Admin;
import com.rebirthlab.gradebook.domain.model.user.AdminRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiy
 */
@Service
public class AdminServiceImpl extends AbstractUserServiceImpl implements AdminService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    private AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    @Transactional
    public Optional<Admin> register(AdminDTO adminDTO) {
        if (adminRepository.existsByEmail(adminDTO.getEmail())) {
            LOGGER.info("Cannot register admin. Admin account {} already exists", adminDTO.getEmail());
            return Optional.empty();
        }
        Admin admin = mapNewAdminEntity(adminDTO);
        return Optional.of(adminRepository.save(admin));
    }

    @Override
    @Transactional
    public Optional<Admin> updateById(Long id, AdminDTO adminDTO) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (!admin.isPresent()) {
            LOGGER.info("Cannot update admin. Admin id {} is not found", id);
            return Optional.empty();
        }
        patchAdminDTO(adminDTO, admin.get());
        Admin updatedAdmin = mapUpdatedAdminEntity(adminDTO);
        return Optional.of(adminRepository.save(updatedAdmin));
    }

    @Override
    @Transactional
    public Optional<Admin> updateByEmail(String email, AdminDTO adminDTO) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (!admin.isPresent()) {
            LOGGER.info("Cannot update admin. Admin email {} is not found", email);
            return Optional.empty();
        }
        patchAdminDTO(adminDTO, admin.get());
        Admin updatedAdmin = mapUpdatedAdminEntity(adminDTO);
        return Optional.of(adminRepository.save(updatedAdmin));
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Optional<List<Admin>> findAll() {
        List<Admin> admins = new ArrayList<>();
        adminRepository.findAll().forEach(admins::add);
        return admins.isEmpty() ? Optional.empty() : Optional.of(admins);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        adminRepository.deleteById(id);
        LOGGER.info("Admin with id {} was successfully deleted", id);
    }

    private void patchAdminDTO(AdminDTO adminDTO, Admin admin) {
        patchAbstractUserDTO(adminDTO, admin);
    }

    private Admin mapNewAdminEntity(AdminDTO adminDTO) {
        return new Admin(adminDTO.getEmail(),
                adminDTO.getFirstName(),
                adminDTO.getLastName());
    }

    private Admin mapUpdatedAdminEntity(AdminDTO adminDTO) {
        return new Admin(adminDTO.getId(),
                adminDTO.getEmail(),
                adminDTO.getFirstName(),
                adminDTO.getLastName());
    }
}
