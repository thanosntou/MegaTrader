package com.ntouzidis.cooperative.module.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> getAllSortedAndOrdered(String smb, String omb);
}
