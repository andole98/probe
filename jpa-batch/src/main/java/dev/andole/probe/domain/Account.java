package dev.andole.probe.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter

@Entity
public class Account extends BaseEntity {

    @Column(length = 10)
    private String name;

    @Embedded
    private AccountNumber accountNumber;

    @OneToMany(mappedBy = "from", cascade = CascadeType.PERSIST)
    private List<Exchange> withdraws = new ArrayList<>();

    @OneToMany(mappedBy = "to", cascade = CascadeType.PERSIST)
    private List<Exchange> deposits = new ArrayList<>();

    @Setter
    private BigDecimal balance;

    public Account(String name) {
        this(name, BigDecimal.ZERO);
    }

    public Account(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
        this.accountNumber = new AccountNumber();
    }

    public void withdraw(Account to, BigDecimal amount) {
        Exchange exchange = new Exchange(this, to, amount);
        withdraws.add(exchange);
        to.deposit(exchange);
    }

    public void deposit(Exchange exchange) {
        this.deposits.add(exchange);
    }

    public String getAccountNumber() {
        return accountNumber.getValue();
    }
}
