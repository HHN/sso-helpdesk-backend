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

    public QRGenerator(@Value("${hhn.fop.qr.width}") Integer width, @Value("${hhn.fop.qr.height}") Integer height) {
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
                    .withHint(EncodeHintType.MARGIN, 0)
                    //Q => 25% error correction level
                    .withErrorCorrection(ErrorCorrectionLevel.Q)
                    .svg(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }
}
