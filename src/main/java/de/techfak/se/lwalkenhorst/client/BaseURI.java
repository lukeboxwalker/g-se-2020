package de.techfak.se.lwalkenhorst.client;

import java.net.URI;

public class BaseURI {

    private final URI base;

    public BaseURI(final URI base) {
        this.base = base;
    }

    public URI append(final String uri) {
        return URI.create(base.getPath() + uri);
    }

    public URI toURI() {
        return base;
    }
}
