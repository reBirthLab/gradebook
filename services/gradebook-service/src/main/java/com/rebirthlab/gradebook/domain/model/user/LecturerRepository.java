package com.rebirthlab.gradebook.domain.model.user;

import com.rebirthlab.gradebook.domain.model.department.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface LecturerRepository extends CrudRepository<Lecturer, Long> {

    @Cacheable("lecturer-cache")
    Optional<Lecturer> findByEmail(String email);

    @Cacheable("lecturer-cache")
    List<Lecturer> findAllByDepartmentId(Department departmentId);

    @Cacheable("lecturer-cache")
    boolean existsByEmail(String email);

    @Override
    @CacheEvict(value = "lecturer-cache", allEntries = true)
    <S extends Lecturer> S save(S entity);

    @Override
    @CacheEvict(value = "lecturer-cache", allEntries = true)
    <S extends Lecturer> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    @Cacheable("lecturer-cache")
    Optional<Lecturer> findById(Long id);

    @Override
    @Cacheable("lecturer-cache")
    boolean existsById(Long id);

    @Override
    @Cacheable("lecturer-cache")
    Iterable<Lecturer> findAll();

    @Override
    @Cacheable("lecturer-cache")
    Iterable<Lecturer> findAllById(Iterable<Long> ids);

    @Override
    @CacheEvict(value = "lecturer-cache", allEntries = true)
    void deleteById(Long id);

    @Override
    @CacheEvict(value = "lecturer-cache", allEntries = true)
    void delete(Lecturer entity);

    @Override
    @CacheEvict(value = "lecturer-cache", allEntries = true)
    void deleteAll(Iterable<? extends Lecturer> entities);

    @Override
    @CacheEvict(value = "lecturer-cache", allEntries = true)
    void deleteAll();
}
