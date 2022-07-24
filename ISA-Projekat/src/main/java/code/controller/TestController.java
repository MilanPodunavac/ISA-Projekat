package code.controller;

import code.model.Admin;
import code.model.Role;
import code.model.User;
import code.service.AdminService;
import code.service.RoleService;
import code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> loadAll() {
        return this.userService.findAll();
    }

    @GetMapping(value = "/whoami", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public User user(Principal user) {
        return this.userService.findByEmail(user.getName());
    }

    @GetMapping(value = "/boo", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public Map<String, String> getBoo() {
        Map<String, String> fooObj = new HashMap<>();
        fooObj.put("BOO", "BOOO");
        return fooObj;
    }

    @GetMapping(value = "/noo", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public Map<String, String> getNoo() {
        Map<String, String> fooObj = new HashMap<>();
        fooObj.put("NOO", "NOOO");
        return fooObj;
    }

    @GetMapping(value = "/joo", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
    public Map<String, String> getJoo() {
        Map<String, String> fooObj = new HashMap<>();
        fooObj.put("JOO", "JOOO");
        return fooObj;
    }

    @GetMapping(value = "/foo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getFoo() {
        Map<String, String> fooObj = new HashMap<>();
        fooObj.put("foo", "bar");
        return fooObj;
    }
}
