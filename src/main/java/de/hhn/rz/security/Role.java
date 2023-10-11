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
package de.hhn.rz.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {

    HHN_HELPDESK_ADMIN("HHN_HELPDESK_ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public GrantedAuthority asGrantedAuthority() {
        return new SimpleGrantedAuthority(withPrefix());
    }

    public String withPrefix() {
        return "ROLE_" + this.toString();
    }
    public String toString() {
        return role;
    }
}
