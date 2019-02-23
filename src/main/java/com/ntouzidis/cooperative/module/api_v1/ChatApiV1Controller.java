package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.service.BitmexService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatApiV1Controller {

    private final BitmexService bitmexService;

    public ChatApiV1Controller(BitmexService bitmexService) {
        this.bitmexService = bitmexService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<?>> getMessages(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        List<Map<String, Object>> messages = bitmexService.get_chat(userDetails.getUser());

        return new ResponseEntity<>(messages, HttpStatus.OK);

    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, Object>> sendMessage(
            Authentication authentication,
            @RequestParam("message") String message
    ) {

        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Map<String, Object> messageResult = bitmexService.post_chat(user, message);

        return new ResponseEntity<>(messageResult, HttpStatus.OK);

    }
}
