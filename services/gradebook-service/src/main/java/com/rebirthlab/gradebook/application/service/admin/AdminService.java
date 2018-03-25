package com.rebirthlab.gradebook.application.service.admin;

import com.rebirthlab.gradebook.domain.model.user.Admin;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface AdminService {

    Optional<Admin> register(AdminDTO adminDTO);

    Optional<Admin> updateById(Long id, AdminDTO adminDTO);

    Optional<Admin> findById(Long id);

    Optional<List<Admin>> findAll();

    void delete(Long id);
}
