package com.rebirthlab.gradebook.domain.model.user;

import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface AdminRepository extends CrudRepository<Admin, Long> {

    @Cacheable("admin-cache")
    Optional<Admin> findByEmail(String email);

    @Cacheable("admin-cache")
    boolean existsByEmail(String email);

    @Override
    @CacheEvict(value = "admin-cache", allEntries = true)
    <S extends Admin> S save(S entity);

    @Override
    @CacheEvict(value = "admin-cache", allEntries = true)
    <S extends Admin> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    @Cacheable("admin-cache")
    Optional<Admin> findById(Long id);

    @Override
    @Cacheable("admin-cache")
    boolean existsById(Long id);

    @Override
    @Cacheable("admin-cache")
    Iterable<Admin> findAll();

    @Override
    @Cacheable("admin-cache")
    Iterable<Admin> findAllById(Iterable<Long> ids);

    @Override
    @CacheEvict(value = "admin-cache", allEntries = true)
    void deleteById(Long id);

    @Override
    @CacheEvict(value = "admin-cache", allEntries = true)
    void delete(Admin entity);

    @Override
    @CacheEvict(value = "admin-cache", allEntries = true)
    void deleteAll(Iterable<? extends Admin> entities);

    @Override
    @CacheEvict(value = "admin-cache", allEntries = true)
    void deleteAll();
}
