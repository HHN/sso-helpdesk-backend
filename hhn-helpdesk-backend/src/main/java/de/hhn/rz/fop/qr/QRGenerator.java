package de.hhn.rz.fop.qr;


import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import de.hhn.rz.AbstractService;
import de.hhn.rz.db.entities.AccountCredential;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class QRGenerator extends AbstractService {

    private final Integer width;
    private final Integer height;

    public QRGenerator(@Value("${hhn.fop.qr.width}") Integer width, @Value("${hhn.fop.base64.height") Integer height) {
        this.width = width;
        this.height = height;
    }

    public String getBase64QR(AccountCredential credential) {
        checkParameter(credential);
        return createBase64Code(credential);
    }

    private String createBase64Code(AccountCredential credential) {
        return "data:image/svg+xml;base64," + new String(Base64.getEncoder().encode(createQR(credential)), StandardCharsets.UTF_8);
    }

    private byte[] createQR(AccountCredential credential) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            QRCode.from(credential.getSeq())
                    .withSize(width, height)
                    .withHint(EncodeHintType.MARGIN, 1)
                    //Q => 25% error correction level
                    .withErrorCorrection(ErrorCorrectionLevel.Q)
                    .svg(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }
}
