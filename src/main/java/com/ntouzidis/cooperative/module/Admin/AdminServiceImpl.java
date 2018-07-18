package com.ntouzidis.cooperative.module.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin getByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
