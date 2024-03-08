package com.example.bankapplication.entity;

import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.entity.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "debitAccount",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = {MERGE, PERSIST, REFRESH})
    private Set<Transaction> debitTransactions = new HashSet<>();

    @OneToMany(mappedBy = "creditAccount",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = {MERGE, PERSIST, REFRESH})
    private Set<Transaction> creditTransactions = new HashSet<>();

    public Account(String uuid, Client client, String account1, AccountType accountType, AccountStatus accountStatus, int i, String eur) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(client, account.client) && Objects.equals(name, account.name) && type == account.type && status == account.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, name, type, status);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client=" + client +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", balance=" + balance +
                ", currencyCode='" + currencyCode + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
