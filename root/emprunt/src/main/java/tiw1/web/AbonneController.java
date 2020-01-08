package tiw1.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import tiw1.dto.AbonneDto;
import tiw1.service.AbonneService;

import java.util.List;

@RestController
@RequestMapping(path = "/abonnes")
public class AbonneController {

    private final AbonneService abonneService;

    public AbonneController(AbonneService abonneService) {
        this.abonneService = abonneService;
    }

    @ApiOperation(value = "retreive Abonne by id", response = AbonneDto.class, httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved abonne"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Abonne with requested id not found")})
    @GetMapping(path = "/{id}")
    public AbonneDto getAbonneById(
            @ApiParam(value = "id of the abonne you want to get", required = true) @PathVariable(name = "id") Long id) {
        return abonneService.getAbonneById(id);
    }

    @ApiOperation(value = "save a new Abonne", response = AbonneDto.class, httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved abonne"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")})
    @PostMapping(path = "/save")
    public AbonneDto saveAbonne(
            @ApiParam(value = "abonne object that you want to save", required = true) @RequestBody AbonneDto abonneDto) {
        return abonneService.saveAbonne(abonneDto);
    }

    @ApiOperation(value = "retreive abonnes list", response = List.class, httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved abonnes list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")})
    @RequestMapping
    public List<AbonneDto> getAbonnes() {
        return abonneService.getAbonneList();
    }

}
