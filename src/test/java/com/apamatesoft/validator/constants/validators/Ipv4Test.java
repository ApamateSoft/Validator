package com.apamatesoft.validator.constants.validators;

import com.apamatesoft.validator.constants.Validators;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class Ipv4Test {

    private static final String[] NOT_PERMIT = {
        null,
        "",
        "text",
        "128",
        "10.0.0.256",
        "10.0.0.0.1",
        "ffff::ffff::ffff",
        "fffff::ffff",
        "fffg::ffff",
        "ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff",
        "ffff::",
        "ffff::ffff",
        "ffff:ffff::ffff"
    };
    private static final String[] PERMIT = { "127.0.0.1", "192.168.0.109", "10.0.0.1" };

    @Test
    void notPermit() {
        assertFalse(stream(NOT_PERMIT).anyMatch(Validators::ipv4));
    }

    @Test
    void permit() {
        assertTrue(stream(PERMIT).allMatch(Validators::ipv4));
    }

}
