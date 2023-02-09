package de.hhn.rz.fop.asf;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * An implementation of {@link ContentHandler} which acts as a proxy to another {@link ContentHandler} and has the
 * purpose to provide a handy methods to simplify the generation of SAX events.
 * <br>
 * Note: This class is only useful for simple cases with no namespaces.
 * <br>
 * It is copied from
 * <a href="https://github.com/apache/xmlgraphics-fop/blob/trunk/fop/examples/embedding/java/embedding/tools/EasyGenerationContentHandlerProxy.java">GitHub</a>
 */
public class EasyGenerationContentHandlerProxy implements ContentHandler {

    /** An empty Attributes object used when no attributes are needed. */
    private static final Attributes EMPTY_ATTS = new AttributesImpl();

    private final ContentHandler target;

    /**
     * Main constructor.
     * @param forwardTo {@link ContentHandler} to forward the SAX event to.
     */
    public EasyGenerationContentHandlerProxy(ContentHandler forwardTo) {
        this.target = forwardTo;
    }


    /**
     * Sends the notification of the beginning of an element.
     * @param name Name for the element.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void startElement(String name) throws SAXException {
        startElement(name, EMPTY_ATTS);
    }


    /**
     * Sends the notification of the beginning of an element.
     * @param name Name for the element.
     * @param atts The {@link Attributes attributes} attached to the element. If there are no attributes,
     *             it shall be an empty {@link Attributes} object.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void startElement(String name, Attributes atts) throws SAXException {
        startElement(null, name, name, atts);
    }


    /**
     * Send a String of character data.
     * @param s The content String
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void characters(String s) throws SAXException {
        target.characters(s.toCharArray(), 0, s.length());
    }


    /**
     * Send the notification of the end of an element.
     * @param name Name for the element.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void endElement(String name) throws SAXException {
        endElement(null, name, name);
    }


    /**
     * Sends notifications for a whole element with some String content.
     * @param name Name for the element.
     * @param value Content of the element.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void element(String name, String value) throws SAXException {
        element(name, value, EMPTY_ATTS);
    }


    /**
     * Sends notifications for a whole element with some String content.
     * @param name Name for the element.
     * @param value Content of the element.
     * @param atts The attributes attached to the element. If there are no
     * attributes, it shall be an empty Attributes object.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void element(String name, String value, Attributes atts) throws SAXException {
        startElement(name, atts);
        if (value != null) {
            characters(value.toCharArray(), 0, value.length());
        }
        endElement(name);
    }

    /* =========== ContentHandler interface =========== */

    /**
     * @see ContentHandler#setDocumentLocator(Locator)
     */
    @Override
    public void setDocumentLocator(Locator locator) {
        target.setDocumentLocator(locator);
    }


    /**
     * @see ContentHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
        target.startDocument();
    }


    /**
     * @see ContentHandler#endDocument()
     */
    @Override
    public void endDocument() throws SAXException {
        target.endDocument();
    }


    /**
     * @see ContentHandler#startPrefixMapping(String, String)
     */
    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        target.startPrefixMapping(prefix, uri);
    }


    /**
     * @see ContentHandler#endPrefixMapping(String)
     */
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        target.endPrefixMapping(prefix);
    }


    /**
     * @see ContentHandler#startElement(String, String, String, Attributes)
     */
    @Override
    public void startElement(String namespaceURI, String localName,
                        String qName, Attributes atts) throws SAXException {
        target.startElement(namespaceURI, localName, qName, atts);
    }


    /**
     * @see ContentHandler#endElement(String, String, String)
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
                                                        throws SAXException {
        target.endElement(namespaceURI, localName, qName);
    }


    /**
     * @see ContentHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        target.characters(ch, start, length);
    }


    /**
     * @see ContentHandler#ignorableWhitespace(char[], int, int)
     */
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        target.ignorableWhitespace(ch, start, length);
    }


    /**
     * @see ContentHandler#processingInstruction(String, String)
     */
    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        this.target.processingInstruction(target, data);
    }


    /**
     * @see ContentHandler#skippedEntity(String)
     */
    @Override
    public void skippedEntity(String name) throws SAXException {
        target.skippedEntity(name);
    }

}