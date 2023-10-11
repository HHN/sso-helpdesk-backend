/*
 * Copyright © 2023 Hochschule Heilbronn (ticket@hs-heilbronn.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.hhn.rz.fop.xto;

import de.hhn.rz.fop.FopConstants;
import de.hhn.rz.fop.asf.AbstractObjectReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.util.List;

public record FopAccountCredentials(List<FopAccountElement> elements) {

    public Source asSource() {
        return new SAXSource(new AccountXMLReader(), new JobInputSource(this));
    }

    private static class JobInputSource extends InputSource {

        private final FopAccountCredentials account;

        public JobInputSource(FopAccountCredentials job) {
            this.account = job;
        }

        public FopAccountCredentials getAccount() {
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

        private void parse(FopAccountCredentials accounts) throws SAXException {
            if (accounts == null) {
                throw new IllegalArgumentException("Parameter account must not be null");
            }
            if (handler == null) {
                throw new IllegalStateException("ContentHandler not set. This should not happen?!");
            }

            handler.startDocument();
            handler.startElement(FopConstants.HHN_ROOT);
            toXML(accounts);
            handler.endElement(FopConstants.HHN_ROOT);
            handler.endDocument();
        }

        private void toXML(FopAccountCredentials accounts) throws SAXException {
            for(FopAccountElement e : accounts.elements) {
                handler.startElement(FopConstants.HHN_ACCOUNT_ELEMENT);
                handler.element(FopConstants.HHN_QR_CODE, e.base64());
                handler.element(FopConstants.HHN_SEQ_NUMBER, e.credential().getSeq());
                handler.element(FopConstants.HHN_PASSWORD, e.credential().getPassword());
                handler.endElement(FopConstants.HHN_ACCOUNT_ELEMENT);
            }


        }

    }
}
