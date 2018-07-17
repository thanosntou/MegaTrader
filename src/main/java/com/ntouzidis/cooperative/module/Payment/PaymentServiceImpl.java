package com.ntouzidis.cooperative.module.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public List<Payment> getAllSortedAndOrdered(String smb, String omb) {
        Sort sort = new Sort((omb.equals("asc")?Sort.Direction.ASC:Sort.Direction.DESC), smb);
        return paymentRepository.findAll(sort);
    }
}
