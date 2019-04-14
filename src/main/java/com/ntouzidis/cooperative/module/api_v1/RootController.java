package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.RootService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/root")
public class RootController {

  private final RootService rootService;

  public RootController(RootService rootService) {
    this.rootService = rootService;
  }

  @PostMapping("/setup")
  public ResponseEntity<User> createRootUser(@RequestParam(name = "pass") String pass) {
    return ResponseEntity.ok(rootService.setuUp(pass));
  }



}
