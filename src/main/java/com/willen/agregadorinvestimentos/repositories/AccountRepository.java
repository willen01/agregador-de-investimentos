package com.willen.agregadorinvestimentos.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.willen.agregadorinvestimentos.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

}
