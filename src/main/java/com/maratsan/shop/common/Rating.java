package com.maratsan.shop.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rating {
    BAD(1),
    AVERAGE(2),
    GOOD(3),
    EXCELLENT(4);

    private final int value;

}
