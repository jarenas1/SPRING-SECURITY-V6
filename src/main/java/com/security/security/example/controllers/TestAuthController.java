package com.security.security.example.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@PreAuthorize("denyAll()") //TODO REQUEST QUE ENTRE SE DENEGARA A NO SER DE QUE EL METODO LO CONFIGURE
public class TestAuthController {

    @PreAuthorize(("permitAll()")) //pERMITE TODOS LOS REQUEST
    @GetMapping("/index")
    public String index() {
        return "Hello World";
    }

    @PreAuthorize("hasAnyAuthority('READ')") //debe tener permiso de leer, tener en cuenta las comillas
    @GetMapping("/index2")
    public String index2() {
        return "Hello World wo security";
    }

    @GetMapping("/index3")
    public String juan() {
        return "Hello World wo security";
    }
}
