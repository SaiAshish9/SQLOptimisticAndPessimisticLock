package com.sai.locking.repository;

import com.sai.locking.entity.CustomerEntity;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {

    @Transactional
    @Override
    <S extends CustomerEntity> S save(S entity);

    @Query("select c from CustomerEntity c where c.firstName = :firstName")
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Transactional
    List<CustomerEntity> findAllByName(@Param("firstName") String firstName);
//    while reading data we can't write or delete data
//    eliminates dirt reads & we can simply read correct data
//    shared lock

    @Modifying
    @Transactional
    @Query("update CustomerEntity c set c.version = c.version + 1 where c.id = :id and c.version = :version")
    int findByIdAndUpdate(@Param("id") String id, @Param("version") int version);
//    we are going to update only when currentVersion at SQL is equal to provided version otherwise not.
//    We can retry updating. This can happen due to concurrency.

}
