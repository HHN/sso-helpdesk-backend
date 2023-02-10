package de.hhn.rz.fop;

import org.apache.fop.apps.io.ResourceResolverFactory;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

/**
 * This class is used to load images and fonts from the jar the application is packaged in. Therefore, e.g. images
 * have to be prefixed with "classpath:" in the corresponding xsl stylesheet to be found properly.
 */
public class FopClasspathPathResolver implements ResourceResolver {

    private static final Logger logger = LoggerFactory.getLogger(FopClasspathPathResolver.class);

    private final ResourceResolver wrapped;

    /**
     * @see FopClasspathPathResolver
     */
    FopClasspathPathResolver() {
        this.wrapped = ResourceResolverFactory.createDefaultResourceResolver();
    }

    @Override
    public Resource getResource(URI uri) throws IOException {
        if (uri == null) {
            throw new IOException("Resource is null.");
        }
        if (uri.getScheme().equals("classpath")) {
            URL url = Thread.currentThread().getContextClassLoader().getResource(uri.getSchemeSpecificPart());
            if (url != null) {
                logger.debug("Resource loaded: {} from the archive.", url.getPath());
            } else {
                throw new IOException("Can't load resource {} from the archive");
            }
            return new Resource(url.openStream());
        } else {
            logger.debug("Resource loaded: {}", uri.getPath());
            return wrapped.getResource(uri);
        }
    }


    @Override
    public OutputStream getOutputStream(URI uri) throws IOException {
        return wrapped.getOutputStream(uri);
    }
}