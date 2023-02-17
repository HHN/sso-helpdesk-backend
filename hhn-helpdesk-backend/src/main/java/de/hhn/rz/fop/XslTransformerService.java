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
package de.hhn.rz.fop;

import de.hhn.rz.AbstractService;
import de.hhn.rz.crypt.Decryptor;
import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.fop.exception.FopRuntimeException;
import de.hhn.rz.fop.qr.QRGenerator;
import de.hhn.rz.fop.xto.FopAccountCredentials;
import de.hhn.rz.fop.xto.FopAccountElement;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class XslTransformerService extends AbstractService {

    private final FopFactory fopFactory;
    private final QRGenerator qrGenerator;
    private final Decryptor decryptor;

    public XslTransformerService(@Autowired FopFactory fopFactory, @Autowired QRGenerator qrGenerator, @Autowired Decryptor decryptor) {
        this.fopFactory = fopFactory;
        this.qrGenerator = qrGenerator;
        this.decryptor = decryptor;
    }

    public byte[] process(List<AccountCredential> credentials) {
        checkParameter(credentials);
        if(credentials.isEmpty()) {
            throw new IllegalArgumentException("Credentials must not be empty");
        }

        try (ByteArrayOutputStream pdfDoc = new ByteArrayOutputStream()) {
            final Fop fop = fopFactory.newFop("application/pdf", pdfDoc);
            final TransformerFactory factory = TransformerFactory.newInstance();
            try (InputStream xslt = Thread.currentThread().getContextClassLoader().getResourceAsStream("hhn.xslt")) {
                final Transformer transformer = factory.newTransformer(new StreamSource(xslt));

                final List<FopAccountElement> elements = new ArrayList<>();

                for (AccountCredential ac : credentials) {
                    ac.setPassword(decryptor.decrypt(ac.getPassword(), ac.getSalt(), ac.getIv()));
                    elements.add(new FopAccountElement(ac, qrGenerator.getBase64QR(ac)));
                }
                transformer.transform(new FopAccountCredentials(elements).asSource(), new SAXResult(fop.getDefaultHandler()));
            } catch (TransformerException e) {
                throw new FopRuntimeException(e);
            }
            return pdfDoc.toByteArray();
        } catch (IOException | FOPException e) {
            throw new FopRuntimeException(e);
        }
    }


}
