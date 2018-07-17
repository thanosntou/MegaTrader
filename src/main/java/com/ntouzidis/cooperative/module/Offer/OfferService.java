package com.ntouzidis.cooperative.module.Offer;

import java.util.List;

public interface OfferService {

    List<Offer> getAllSortedAndOrdered(String smb, String omb);

}
