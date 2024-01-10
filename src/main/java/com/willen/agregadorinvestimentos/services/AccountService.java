package com.willen.agregadorinvestimentos.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.willen.agregadorinvestimentos.controllers.dto.AssociateAccountStockDto;
import com.willen.agregadorinvestimentos.entities.AccountStock;
import com.willen.agregadorinvestimentos.entities.AccountStockId;
import com.willen.agregadorinvestimentos.repositories.AccountRepository;
import com.willen.agregadorinvestimentos.repositories.AccountStockRepository;
import com.willen.agregadorinvestimentos.repositories.StockRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository,
            StockRepository stockRepository,
            AccountStockRepository accountStockRepository) {

        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void assiciateStock(String accountId, AssociateAccountStockDto dto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // DTO -> entity
        var id = new AccountStockId(account.getAccountId(), stock.getStockId());

        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity());

        accountStockRepository.save(entity);
    }
}
