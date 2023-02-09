package de.hhn.rz.schema;

import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.db.entities.AuditLogEntry;
import de.hhn.rz.db.entities.Location;
import net.bytebuddy.asm.Advice;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

    public static void main(String[] args) {
        generateSchema();
    }
    public static void generateSchema() {
        Map<String, String> settings = new HashMap<>();
        settings.put(Environment.URL, "jdbc:h2:mem:schema");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(AccountCredential.class);
        metadataSources.addAnnotatedClass(AuditLogEntry.class);
        metadataSources.addAnnotatedClass(Location.class);
        Metadata metadata = metadataSources.buildMetadata();

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setFormat(true);
        schemaExport.setOutputFile("create.sql");
        schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);
    }
}