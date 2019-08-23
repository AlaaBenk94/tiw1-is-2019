package tiw1.maintenance.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiw1.maintenance.metier.Maintenance;
import tiw1.maintenance.models.Trottinette;

import java.util.List;

@RestController
@RequestMapping("/trottinette")
public class TrottinetteController {

    @Autowired
    private Maintenance m;

    @GetMapping
    public List<Trottinette> getTrottinettes() {
        return m.getTrottinettes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trottinette> getTrottinette(@PathVariable long id) {
        final Trottinette trottinette = m.getTrottinette(id);
        if (trottinette == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(trottinette, HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<Trottinette> addTrottinette() {
        return new ResponseEntity<Trottinette>(m.creerTrottinette(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trottinette> updateTrottinette(@PathVariable long id, @RequestBody Trottinette t) {
        t.setId(id);
        Trottinette resp = m.updateTrottinette(t);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTrottinette(@PathVariable long id) {
        m.supprimerTrottinette(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
