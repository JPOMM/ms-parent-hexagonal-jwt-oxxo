package com.oxxo.scr.ms.maintance.maintenance.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @RequestMapping("/")
  public String home() {
    return "The microservice APPMIC-E-CommercePlugins-maintenance has been activated";
  }
}
