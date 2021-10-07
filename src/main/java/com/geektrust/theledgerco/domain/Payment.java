package com.geektrust.theledgerco.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Payment {
    private final Double lumpSumAmount;
    private final Integer emiNumber;
}
