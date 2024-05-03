/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.hc.client5.http.impl.auth;

import org.apache.hc.client5.http.DnsResolver;
import org.apache.hc.client5.http.SystemDefaultDnsResolver;
import org.apache.hc.client5.http.auth.AuthScheme;
import org.apache.hc.client5.http.auth.AuthSchemeFactory;
import org.apache.hc.client5.http.auth.KerberosConfig;
import org.apache.hc.client5.http.impl.auth.SPNegoScheme;
import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.Experimental;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.protocol.HttpContext;

@Contract(threading=ThreadingBehavior.STATELESS)
@Experimental
public class SPNegoSchemeFactory
implements AuthSchemeFactory {
    public static final SPNegoSchemeFactory DEFAULT = new SPNegoSchemeFactory(KerberosConfig.DEFAULT, SystemDefaultDnsResolver.INSTANCE);
    private final KerberosConfig config;
    private final DnsResolver dnsResolver;

    public SPNegoSchemeFactory(KerberosConfig config, DnsResolver dnsResolver) {
        this.config = config;
        this.dnsResolver = dnsResolver;
    }

    @Override
    public AuthScheme create(HttpContext context) {
        return new SPNegoScheme(this.config, this.dnsResolver);
    }
}

