package com.ntouzidis.cooperative.module.member;

import java.util.List;

public interface MemberService {

    Member getByUsername(String username);

    List<Member> getAllSortedAndOrdered(String smb, String omb);

    Member get(int id);

    Member save(Member m);


}
