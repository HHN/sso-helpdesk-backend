package de.hhn.rz.fop;

import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.fop.qr.QRGenerator;
import org.apache.fop.apps.FopFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FopTest {


    public static void main(String[] args) throws IOException {


        FopFactoryProducer producer = new FopFactoryProducer(300);
        FopFactory factory = producer.fopFactory();

        QRGenerator qr = new QRGenerator(64,64);

        XslTransformerService xslTransformerService = new XslTransformerService(factory, qr);

        AccountCredential ac = new AccountCredential();
        ac.setSeq("12345678");
        ac.setPassword("bla bla");


        byte[] result = xslTransformerService.process(ac);

        Files.write(Paths.get("test-"+ System.currentTimeMillis() + ".pdf"), result, StandardOpenOption.CREATE_NEW);

    }
}
