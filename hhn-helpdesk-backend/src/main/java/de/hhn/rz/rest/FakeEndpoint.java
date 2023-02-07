package de.hhn.rz.rest;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//FIXME REMOVE
@RestController
@RequestMapping("/")
public class FakeEndpoint {

    @GetMapping("")
    public String get() {
        return SecurityContextHolder.getContext().getAuthentication().toString();
    }
}
