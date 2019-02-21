package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.service.BitmexService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/announcements")
public class NewsApiV1Controller {

    private final UserService userService;
    private final BitmexService bitmexService;

    public NewsApiV1Controller(UserService userService, BitmexService bitmexService) {
        this.userService = userService;
        this.bitmexService = bitmexService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        User user = userService.findByUsername("pelaths").orElse(null);

        List<Map<String, Object>> announcements = bitmexService.get_Announcements(user);

        return ResponseEntity.ok(announcements);
    }
}
