package code.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping(value = "/getTest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test() {
        return new ResponseEntity<String>("Test", HttpStatus.OK);
    }
}
