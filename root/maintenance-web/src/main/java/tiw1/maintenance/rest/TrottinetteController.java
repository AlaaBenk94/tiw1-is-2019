package tiw1.maintenance.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrottinetteController {

    @RequestMapping("/trottinette")
    public String getTrottinettes() {
        return "[]";
    }
}
