package de.hhn.rz.fop;

import de.hhn.rz.fop.exception.FopRuntimeException;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.ConfigurationException;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import org.apache.fop.image.loader.batik.ImageLoaderFactorySVG;
import org.apache.xmlgraphics.image.loader.impl.ImageConverterRendered2PNG;
import org.apache.xmlgraphics.image.loader.impl.ImageLoaderFactoryPNG;
import org.apache.xmlgraphics.image.loader.impl.PreloaderRawPNG;
import org.apache.xmlgraphics.image.loader.spi.ImageImplRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Configuration
public class FopFactoryProducer {

    private static final Logger logger = LoggerFactory.getLogger(FopFactoryProducer.class);

    private final Integer dpi;

    public FopFactoryProducer(@Value("${hhn.fop.dpi}") Integer dpi) {
        this.dpi = dpi;
    }

    @Bean
    public FopFactory fopFactory() {
        try (InputStream configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("fop-config.xml")) {

            final FopFactoryBuilder fopBuilder = new FopFactoryBuilder(new File(".").toURI());
            final DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
            final org.apache.fop.configuration.Configuration cfg = cfgBuilder.build(configStream);
            fopBuilder.setConfiguration(cfg);
            fopBuilder.setStrictFOValidation(false);
            fopBuilder.setBreakIndentInheritanceOnReferenceAreaBoundary(false);
            fopBuilder.setSourceResolution(dpi);
            fopBuilder.setTargetResolution(dpi);

            //Add specific loader, converter and factory because fop doesn't recognize them on its own...
            ImageImplRegistry r = fopBuilder.getImageManager().getRegistry();
            r.registerPreloader(new PreloaderRawPNG());
            r.registerConverter(new ImageConverterRendered2PNG());
            r.registerLoaderFactory(new ImageLoaderFactoryPNG());
            r.registerLoaderFactory(new ImageLoaderFactorySVG());

            if (logger.isDebugEnabled()) {
                printContent(r.getPreloaderIterator(), "ImagePreloader");
                printContent(r.getImageConverters().iterator(), "ImageConverter");
            }

            return fopBuilder.build();
        } catch (IOException e) {
            throw new FopRuntimeException("Could not load fop config!", e);
        } catch (ConfigurationException e) {
            throw new FopRuntimeException(e);
        }

    }

    private void printContent(Iterator iterator, String category) {
        while (iterator.hasNext()) {
            Object converter = iterator.next();
            logger.debug("Available '{}': {}", category, converter.getClass().getSimpleName());
        }
    }

}
