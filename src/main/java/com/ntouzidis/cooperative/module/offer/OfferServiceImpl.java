package com.ntouzidis.cooperative.module.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Override
    public Offer save(Offer offer) {
        return offerRepository.saveAndFlush(offer);
    }

    @Override
    @Transactional
    public void activate(int id) {
        offerRepository.updateOfferStatus(id, 1);
    }

    @Override
    @Transactional
    public void deactivate(int id) {
        offerRepository.updateOfferStatus(id, 0);
    }


}
