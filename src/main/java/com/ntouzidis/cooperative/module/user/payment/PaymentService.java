package com.ntouzidis.cooperative.module.user.payment;

import java.util.List;

public interface PaymentService {

    List<Payment> getAllSortedAndOrdered(String smb, String omb);
}
