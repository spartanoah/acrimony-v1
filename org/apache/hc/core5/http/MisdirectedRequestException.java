/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.hc.core5.http;

import org.apache.hc.core5.http.ProtocolException;

public class MisdirectedRequestException
extends ProtocolException {
    private static final long serialVersionUID = 1L;

    public MisdirectedRequestException() {
    }

    public MisdirectedRequestException(String message) {
        super(message);
    }
}

