package de.hhn.rz.fop;

import de.hhn.rz.AbstractService;
import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.fop.exception.FopRuntimeException;
import de.hhn.rz.fop.qr.QRGenerator;
import de.hhn.rz.fop.xto.FopAccountXTO;
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

@Service
public class XslTransformerService extends AbstractService {

    private final FopFactory fopFactory;
    private final QRGenerator qrGenerator;

    public XslTransformerService(@Autowired FopFactory fopFactory, @Autowired QRGenerator qrGenerator) {
        this.fopFactory = fopFactory;
        this.qrGenerator = qrGenerator;
    }

    //TODO Most likely should be a list or something ;-)
    public byte[] process(AccountCredential credential) {
        checkParameter(credential);
        try (ByteArrayOutputStream pdfDoc = new ByteArrayOutputStream()) {
            final Fop fop = fopFactory.newFop("application/pdf", pdfDoc);
            final TransformerFactory factory = TransformerFactory.newInstance();
            try (InputStream xslt = Thread.currentThread().getContextClassLoader().getResourceAsStream("hhn.xslt")) {
                final Transformer transformer = factory.newTransformer(new StreamSource(xslt));
                transformer.transform(new FopAccountXTO(credential, qrGenerator.getBase64QR(credential)).asSource(), new SAXResult(fop.getDefaultHandler()));
            } catch (TransformerException e) {
                throw new FopRuntimeException(e);
            }
            return pdfDoc.toByteArray();
        } catch (IOException | FOPException e) {
            throw new FopRuntimeException(e);
        }
    }


}
