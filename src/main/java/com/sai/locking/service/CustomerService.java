package com.sai.locking.service;

import com.sai.locking.dto.CustomerDto;
import com.sai.locking.entity.CustomerEntity;
import com.sai.locking.repository.CustomerRepo;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;

    @Transactional()
    public List<CustomerEntity> findAllByFirstName(String firstName) {
        try {
            return customerRepo.findAllByName(firstName);
        }catch(OptimisticLockException e){
            log.error("concurrent exception occured");
            return null;
        }
    }

    public CustomerEntity save(CustomerDto customerDto) {
        return customerRepo.save(new CustomerEntity()
                .builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .version(customerDto.getVersion())
                .build());
    }

    @Transactional
    public void findByIdAndUpdate(String id) {
        int count = customerRepo.findByIdAndUpdate(id, 287587);
        if(count != 1) {
            throw new OptimisticLockException("OptimisticLockException");
        }
    }

}
