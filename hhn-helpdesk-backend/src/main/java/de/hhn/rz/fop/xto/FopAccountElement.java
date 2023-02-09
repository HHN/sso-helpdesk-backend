package de.hhn.rz.fop.xto;

import de.hhn.rz.db.entities.AccountCredential;

public record FopAccountElement(AccountCredential credential, String base64) {
}
