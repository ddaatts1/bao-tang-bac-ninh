package com.baotangbacninh.baotang.repository;

import com.baotangbacninh.baotang.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    public Account findByAccountName(String accountname);
}
