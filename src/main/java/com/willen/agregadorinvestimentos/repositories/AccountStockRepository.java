package com.willen.agregadorinvestimentos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.willen.agregadorinvestimentos.entities.AccountStock;
import com.willen.agregadorinvestimentos.entities.AccountStockId;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {

}
