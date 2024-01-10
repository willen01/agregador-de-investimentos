package com.willen.agregadorinvestimentos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.willen.agregadorinvestimentos.controllers.dto.AssociateAccountStockDto;
import com.willen.agregadorinvestimentos.services.AccountService;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
            @RequestBody AssociateAccountStockDto dto) {

        accountService.assiciateStock(accountId, dto);
        return ResponseEntity.ok().build();
    }

}
