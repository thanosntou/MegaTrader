package com.ntouzidis.cooperative.module.Sale;

import java.util.List;

public interface SaleService {

    List<Sale> getAllSortedAndOrdered(String smb, String omb);
}
