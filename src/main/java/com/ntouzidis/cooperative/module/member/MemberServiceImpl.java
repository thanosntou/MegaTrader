package com.ntouzidis.cooperative.module.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Member get(int id) {
        return memberRepository.getOne(id);
    }

    @Override
    public Member getByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public List<Member> getAllSortedAndOrdered(String smb, String omb) {
        Sort sort = new Sort((omb.equals("asc")?Sort.Direction.ASC:Sort.Direction.DESC), smb);
        return memberRepository.findAll(sort);
    }

    @Override
    public Member save(Member m) {
        return memberRepository.saveAndFlush(m);
    }



}
