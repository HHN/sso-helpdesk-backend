package de.hhn.rz.fop.xto;

import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.fop.FopJobConstants;
import de.hhn.rz.fop.asf.AbstractObjectReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

public record FopAccountXTO(AccountCredential credential, String base64) {

    public Source asSource() {
        return new SAXSource(new AccountXMLReader(), new JobInputSource(this));
    }

    private static class JobInputSource extends InputSource {

        private final FopAccountXTO account;

        public JobInputSource(FopAccountXTO job) {
            this.account = job;
        }

        public FopAccountXTO getAccount() {
            return account;
        }

    }

    private static class AccountXMLReader extends AbstractObjectReader {

        /**
         * {@inheritDoc}
         */
        public void parse(InputSource input) throws SAXException {
            if (input instanceof JobInputSource) {
                parse(((JobInputSource) input).getAccount());
            } else {
                throw new SAXException("Unsupported InputSource specified. Must be a JobInputSource");
            }
        }

        private void parse(FopAccountXTO account) throws SAXException {
            if (account == null) {
                throw new IllegalArgumentException("Parameter account must not be null");
            }
            if (handler == null) {
                throw new IllegalStateException("ContentHandler not set. This should not happen?!");
            }

            handler.startDocument();
            toXML(account);
            handler.endDocument();
        }

        private void toXML(FopAccountXTO fopJob) throws SAXException {
            handler.startElement(FopJobConstants.HHN_ROOT);
            handler.element(FopJobConstants.HHN_QR_CODE, fopJob.base64);
            handler.element(FopJobConstants.HHN_SEQ_NUMBER, fopJob.credential.getSeq());
            handler.element(FopJobConstants.HHN_PASSWORD, fopJob.credential.getPassword());
            handler.endElement(FopJobConstants.HHN_ROOT);
        }

    }
}
