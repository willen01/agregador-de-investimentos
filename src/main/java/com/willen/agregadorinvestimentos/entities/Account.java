package com.willen.agregadorinvestimentos.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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

    @OneToOne(mappedBy = "account") // O relacionamento está sendo mapeado do outro lado pelo atributo account
    @PrimaryKeyJoinColumn // O relacionamento vai se dar passando a pk de Account para tabela de
                          // BillingAddress
    private BillingAddress billingAddress;

    @OneToMany(mappedBy = "account")
    private List<AccountStock> accountStock;

    public Account() {
    }

    public Account(UUID accountId, User user, String descriprion, BillingAddress billingAddress,
            List<AccountStock> accountStock) {
        this.accountId = accountId;
        this.user = user;
        this.descriprion = descriprion;
        this.billingAddress = billingAddress;
        this.accountStock = accountStock;
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
