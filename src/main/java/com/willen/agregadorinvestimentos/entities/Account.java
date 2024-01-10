package com.willen.agregadorinvestimentos.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id")
    private UUID accountId;

    @ManyToOne
    @JoinColumn(name = "user_id") // essa será a fk da tabela tb_accounts para users
    private User user;

    @Column(name = "description")
    private String descriprion;

    public Account() {
    }

    public Account(UUID accountId, String descriprion) {
        this.accountId = accountId;
        this.descriprion = descriprion;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getDescriprion() {
        return descriprion;
    }

    public void setDescriprion(String descriprion) {
        this.descriprion = descriprion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
