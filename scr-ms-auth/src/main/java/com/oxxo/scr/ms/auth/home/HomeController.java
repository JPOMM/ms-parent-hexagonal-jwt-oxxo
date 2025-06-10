package com.oxxo.scr.ms.auth.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @RequestMapping("/")
  public String home() {
    return "The microservice scr-ms-auth has been activated";
  }
}
