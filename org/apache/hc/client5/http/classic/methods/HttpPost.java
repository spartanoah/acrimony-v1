/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.hc.client5.http.classic.methods;

import java.net.URI;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;

public class HttpPost
extends HttpUriRequestBase {
    private static final long serialVersionUID = 1L;
    public static final String METHOD_NAME = "POST";

    public HttpPost(URI uri) {
        super(METHOD_NAME, uri);
    }

    public HttpPost(String uri) {
        this(URI.create(uri));
    }
}

