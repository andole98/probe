package dev.andole.probe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
public class Exchange extends BaseEntity {

    private BigDecimal money;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account from;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account to;

    public Exchange(Account from, Account to, BigDecimal money) {
        this.money = money;
        this.from = from;
        this.to = to;
    }

    public Exchange revert() {
        return new Exchange(to, from, money);
    }
}
