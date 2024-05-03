/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.hc.core5.util;

import org.apache.hc.core5.util.TextUtils;

public class Asserts {
    private Asserts() {
    }

    public static void check(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void check(boolean expression, String message, Object ... args) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, args));
        }
    }

    public static void check(boolean expression, String message, Object arg) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, arg));
        }
    }

    public static void notNull(Object object, String name) {
        if (object == null) {
            throw new IllegalStateException(name + " is null");
        }
    }

    public static void notEmpty(CharSequence s, String name) {
        if (TextUtils.isEmpty(s)) {
            throw new IllegalStateException(name + " is empty");
        }
    }

    public static void notBlank(CharSequence s, String name) {
        if (TextUtils.isBlank(s)) {
            throw new IllegalStateException(name + " is blank");
        }
    }
}

