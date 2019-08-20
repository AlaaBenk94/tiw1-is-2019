package tiw1.maintenance.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tiw1.maintenance.models.Trottinette;

import java.util.ArrayList;
import java.util.List;

@RestController()
public class TrottinetteController {

    @RequestMapping("/trottinette")
    public List<Trottinette> getTrottinettes() {
        List<Trottinette> t = new ArrayList<>();
        t.add(new Trottinette(1));
        t.add(new Trottinette(5));
        return t;
    }
}
