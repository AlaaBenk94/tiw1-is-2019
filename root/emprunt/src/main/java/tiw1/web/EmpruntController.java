package tiw1.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;
import tiw1.dto.EmpruntDto;
import tiw1.service.EmpruntService;

@RestController
@RequestMapping(path = "/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;

    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    @ApiOperation(value = "retreive emprunt by id", response = EmpruntDto.class, httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved emprunt"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Emprunt with requested id not found")})
    @GetMapping(path = "/{id}")
    public EmpruntDto getEmprunt(
            @ApiParam(value = "id of emprunt you want to get", required = true) @PathVariable(name = "id") Long id) {
        try {
            return empruntService.getEmprunt(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "save an Emprunt", response = EmpruntDto.class, httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved emprunt object"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")})
    @PostMapping(path = "/save")
    public EmpruntDto saveEmprunt(
            @ApiParam(value = "emprunt object that you want to save", required = true) @RequestBody EmpruntDto empruntDto) {
        return empruntService.saveEmprunt(empruntDto);
    }

    @ApiOperation(value = "retreive emprunts list", response = String.class, httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved emprunts list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")})
    @RequestMapping
    public String getEmprunts() {
        return "please, add an id path variable.";
    }
}
