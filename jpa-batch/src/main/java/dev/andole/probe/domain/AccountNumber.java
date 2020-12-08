package dev.andole.probe.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@EqualsAndHashCode
@Getter

@Embeddable
public class AccountNumber {
    @Column(name = "account_number")
    private String value;

    public AccountNumber() {
        value = UUID.randomUUID().toString();
    }
}
