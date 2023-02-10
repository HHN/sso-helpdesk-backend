package de.hhn.rz.utilities;

import de.hhn.rz.crypt.Decryptor;
import de.hhn.rz.crypt.Encryptor;
import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.fop.XslTransformerService;
import de.hhn.rz.fop.FopFactoryProducer;
import de.hhn.rz.fop.qr.QRGenerator;
import org.apache.fop.apps.FopFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.List;

public class FopUtil {

    private static final String SECRET = "TEST";

    public static void main(String[] args) throws IOException {

        FopFactoryProducer producer = new FopFactoryProducer(300);
        FopFactory factory = producer.fopFactory();

        QRGenerator qr = new QRGenerator(64,64);

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        String salt =  "test";

        Encryptor encryptor = new Encryptor(SECRET);
        Decryptor decryptor = new Decryptor(SECRET);

        String c = encryptor.encrypt("passwordtest", salt, iv);

        byte[] iv2 = new byte[16];
        new SecureRandom().nextBytes(iv2);

        String salt2 =  "test";


        String c2 = encryptor.encrypt("passwordtest2", salt2, iv2);


        XslTransformerService xslTransformerService = new XslTransformerService(factory, qr, decryptor);

        AccountCredential ac = new AccountCredential();
        ac.setSeq("12345678");
        ac.setIv(iv);
        ac.setSalt(salt);
        ac.setPassword(c);

        AccountCredential ac2 = new AccountCredential();
        ac2.setSeq("12345678");
        ac2.setIv(iv2);
        ac2.setSalt(salt2);
        ac2.setPassword(c2);


        byte[] result = xslTransformerService.process(List.of(ac,ac2));

        Files.write(Paths.get("test-"+ System.currentTimeMillis() + ".pdf"), result, StandardOpenOption.CREATE_NEW);

    }
}
