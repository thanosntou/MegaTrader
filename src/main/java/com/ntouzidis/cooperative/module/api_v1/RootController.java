package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.RootService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.ntouzidis.cooperative.module.common.ControllerPathsConstants.ROOT_CONTROLLER_PATH;
import static com.ntouzidis.cooperative.module.common.ParamsConstants.PASS_PARAM;

@RestController
@RequestMapping(ROOT_CONTROLLER_PATH)
public class RootController {

  private static final String SETUP_PATH = "/setup";

  private final RootService rootService;

  public RootController(RootService rootService) {
    this.rootService = rootService;
  }

  @PostMapping(SETUP_PATH)
  public ResponseEntity<User> createRootUser(
      @RequestParam(PASS_PARAM) String pass
  ) {
    return ResponseEntity.ok(rootService.setuUp(pass));
  }

}
