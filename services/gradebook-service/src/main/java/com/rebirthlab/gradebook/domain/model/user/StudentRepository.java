package com.rebirthlab.gradebook.domain.model.user;

import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface StudentRepository extends CrudRepository<Student, Long> {

    @Cacheable("student-cache")
    Optional<Student> findByEmail(String email);

    @Cacheable("student-cache")
    boolean existsByEmail(String email);

    @Override
    @CacheEvict(value = "student-cache", allEntries = true)
    <S extends Student> S save(S entity);

    @Override
    @CacheEvict(value = "student-cache", allEntries = true)
    <S extends Student> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    @Cacheable("student-cache")
    Optional<Student> findById(Long id);

    @Override
    @Cacheable("student-cache")
    boolean existsById(Long id);

    @Override
    @Cacheable("student-cache")
    Iterable<Student> findAll();

    @Override
    @Cacheable("student-cache")
    Iterable<Student> findAllById(Iterable<Long> ids);

    @Override
    @CacheEvict(value = "student-cache", allEntries = true)
    void deleteById(Long id);

    @Override
    @CacheEvict(value = "student-cache", allEntries = true)
    void delete(Student entity);

    @Override
    @CacheEvict(value = "student-cache", allEntries = true)
    void deleteAll(Iterable<? extends Student> entities);

    @Override
    @CacheEvict(value = "student-cache", allEntries = true)
    void deleteAll();
}
