package com.willen.agregadorinvestimentos.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.willen.agregadorinvestimentos.clients.BrapiClient;
import com.willen.agregadorinvestimentos.controllers.dto.AccountStockResponseDTO;
import com.willen.agregadorinvestimentos.controllers.dto.AssociateAccountStockDto;
import com.willen.agregadorinvestimentos.entities.AccountStock;
import com.willen.agregadorinvestimentos.entities.AccountStockId;
import com.willen.agregadorinvestimentos.repositories.AccountRepository;
import com.willen.agregadorinvestimentos.repositories.AccountStockRepository;
import com.willen.agregadorinvestimentos.repositories.StockRepository;

@Service
public class AccountService {

    @Value("#{environment.TOKEN}")
    private String TOKEN;

    private AccountRepository accountRepository;

    private StockRepository stockRepository;

    private AccountStockRepository accountStockRepository;

    private BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository,
            StockRepository stockRepository,
            AccountStockRepository accountStockRepository,
            BrapiClient brapiClient) {

        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
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

    public List<AccountStockResponseDTO> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Evitar problemas de serialização de dados
        return account.getAccountStock()
                .stream()
                .map(as -> new AccountStockResponseDTO(
                        as.getStock().getStockId(),
                        as.getQuantity(),
                        getTotal(as.getQuantity(), as.getStock().getStockId())))
                .toList();
    }

    private double getTotal(Integer quantity, String stockId) {
        var response = brapiClient.getQuote(TOKEN, stockId);
        var price = response.results().get(0).regularMarketPrice();

        // Fazer validações para verificar se o objeto não chega vazio
        return quantity * price;
    }
}
