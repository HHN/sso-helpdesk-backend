package de.hhn.rz.dto;

import java.util.Set;

public record UserInfo(String username, Set<String> roles) {
}
