package de.hhn.rz;

import java.util.List;

public class MfaUtil {
    public static boolean isMfa(String type) {
        return List.of("otp", "webauthn").contains(type);
    }
}
