package tiw1.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tiw1.dto.TrottinetteDto;
import tiw1.service.TrottinetteService;

import java.util.List;

@RestController
@RequestMapping(path = "/trottinettes")
public class TrottinetteController {

    private final TrottinetteService trottinetteService;

    public TrottinetteController(TrottinetteService trottinetteService) {
        this.trottinetteService = trottinetteService;
    }

    @ApiOperation(value = "retreive a single trottinette by id", response = TrottinetteDto.class, httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved trottinette"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The trottinette you were trying to get is not found")})
    @GetMapping(path = "/{id}")
    public TrottinetteDto getTrottinetteById(
            @ApiParam(value = "The id of the trottinette you want to get", required = true) @PathVariable(name = "id") Long id) {
        return trottinetteService.getTrottinette(id);
    }

    @ApiOperation(value = "retreive trottinettes list", response = List.class, httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved trottinettes list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")})
    @GetMapping
    public List<TrottinetteDto> getTrottinettes() {
        return trottinetteService.getTrottinettes();
    }
}
