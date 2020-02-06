package tiw1.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tiw1.dto.AbonneDto;
import tiw1.service.AbonneService;

import java.util.List;

import static tiw1.web.util.AuthenticationUtils.isAdmin;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return abonneService.getAbonneById(id, isAdmin(authentication), authentication.getName());
    }

    @ApiOperation(value = "save a new Abonne", response = AbonneDto.class, httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved abonne"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")})
    @PostMapping
    public AbonneDto subscribe(
            @ApiParam(value = "abonne object that you want to save", required = true) @RequestBody AbonneDto abonneDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return abonneService.subscribe(abonneDto, authentication.getName());
    }

    @ApiOperation(value = "delete Abonne", response = AbonneDto.class, httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted abonne"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")})
    @DeleteMapping
    public AbonneDto unsubscribe(
            @ApiParam(value = "abonne object that you want to remove", required = true) @RequestBody AbonneDto abonneDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return abonneService.unsubscribe(abonneDto.getId(), isAdmin(authentication), authentication.getName());
    }

    @ApiOperation(value = "retreive abonnes list", response = List.class, httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved abonnes list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")})
    @GetMapping
    public List<AbonneDto> getAbonnes() {
        return abonneService.getAbonneList();
    }
}
