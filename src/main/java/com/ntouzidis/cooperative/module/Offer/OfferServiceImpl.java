package com.ntouzidis.cooperative.module.Offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Override
    public List<Offer> getAllSortedAndOrdered(String smb, String omb) {
        Sort sort = new Sort((omb.equals("asc")?Sort.Direction.ASC:Sort.Direction.DESC), smb);
        return offerRepository.findAll(sort);
    }


}
