package com.reliaquest.api.id;

import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {

    private static final AtomicInteger counter = new AtomicInteger(1);

    private IDGenerator() {

    }

    public static String getUniqueId() {
        return "ENO-" + counter.getAndIncrement();
    }
}