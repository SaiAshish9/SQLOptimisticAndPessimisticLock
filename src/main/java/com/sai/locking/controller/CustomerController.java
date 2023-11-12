package com.sai.locking.controller;

import com.sai.locking.dto.CustomerDto;
import com.sai.locking.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/all")
    public ResponseEntity findAllByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(customerService.findAllByFirstName(name));
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody CustomerDto customerDto){
        return ResponseEntity.ok(customerService.save(customerDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity findByIdAndUpdate(@PathVariable("id") String id){
        customerService.findByIdAndUpdate(id);
        return ResponseEntity.ok(id);
    }

}
