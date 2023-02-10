package de.hhn.rz.fop.asf;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.util.HashMap;
import java.util.Map;

/**
 * This class can be used as base class for {@link XMLReader readers} that generate SAX
 * events from Java objects.
 * <p>
 * It is a partially modified version from
 * <a href="https://github.com/apache/xmlgraphics-fop/blob/trunk/fop/examples/embedding/java/embedding/tools/AbstractObjectReader.java">GitHub</a>
 */
public abstract class AbstractObjectReader implements XMLReader {
    private static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    private static final String NS_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";

    private final Map<String, Boolean> features = new HashMap<>();
    private ContentHandler orgHandler;

    /**
     * Proxy for easy SAX event generation
     */
    protected EasyGenerationContentHandlerProxy handler;
    /**
     * Error handler
     */
    protected ErrorHandler errorHandler;

    /**
     * Constructor for the AbstractObjectReader object
     */
    public AbstractObjectReader() {
        setFeature(NAMESPACES, false);
        setFeature(NS_PREFIXES, false);
    }

    /* ============ XMLReader interface ============ */

    /**
     * @see XMLReader#getContentHandler()
     */
    @Override
    public ContentHandler getContentHandler() {
        return this.orgHandler;
    }

    /**
     * @see XMLReader#setContentHandler(ContentHandler)
     */
    @Override
    public void setContentHandler(ContentHandler handler) {
        this.orgHandler = handler;
        this.handler = new EasyGenerationContentHandlerProxy(handler);
    }

    /**
     * @see XMLReader#getErrorHandler()
     */
    @Override
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    /**
     * @see XMLReader#setErrorHandler(ErrorHandler)
     */
    @Override
    public void setErrorHandler(ErrorHandler handler) {
        this.errorHandler = handler;
    }

    /**
     * @see XMLReader#getDTDHandler()
     */
    @Override
    public DTDHandler getDTDHandler() {
        return null;
    }

    /**
     * @see XMLReader#setDTDHandler(DTDHandler)
     */
    @Override
    public void setDTDHandler(DTDHandler handler) {
    }

    /**
     * @see XMLReader#getEntityResolver()
     */
    @Override
    public EntityResolver getEntityResolver() {
        return null;
    }

    /**
     * @see XMLReader#setEntityResolver(EntityResolver)
     */
    @Override
    public void setEntityResolver(EntityResolver resolver) {
    }

    /**
     * @see XMLReader#getProperty(String)
     */
    @Override
    public Object getProperty(String name) {
        return null;
    }

    /**
     * @see XMLReader#setProperty(String, Object)
     */
    @Override
    public void setProperty(String name, Object value) {
    }

    /**
     * @see XMLReader#getFeature(String)
     */
    @Override
    public boolean getFeature(String name) {
        return features.get(name);
    }

    /**
     * @see XMLReader#setFeature(String, boolean)
     */
    @Override
    public void setFeature(String name, boolean value) {
        this.features.put(name, value);
    }

    /**
     * @see XMLReader#parse(String)
     */
    @Override
    public void parse(String systemId) throws SAXException {
        throw new SAXException(
                this.getClass().getName()
                        + " cannot be used with system identifiers (URIs)");
    }

    /**
     * @see XMLReader#parse(InputSource)
     */
    @Override
    public abstract void parse(InputSource input) throws SAXException;

}