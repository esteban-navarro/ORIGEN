package cl.origen.platform.modules.health.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.origen.platform.common.constants.ApiMessages;
import cl.origen.platform.common.response.Response;
import cl.origen.platform.common.response.ResponseFactory;
import cl.origen.platform.modules.health.dto.StatusResponse;
import cl.origen.platform.modules.health.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * REST controller that exposes application health endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(
        name = "System",
        description = "System monitoring endpoints."
)
public class StatusController {

    private final StatusService statusService;

    /**
     * Returns application status information.
     *
     * @return application status response.
     */
    @GetMapping("/status")
    @Operation(
            operationId = "getStatus",
            summary = "Application status",
            description = "Returns the current application status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Application is running"
            )
    })
    public ResponseEntity<Response<StatusResponse>> getStatus() {

        return ResponseEntity.ok(
                ResponseFactory.ok(
                        ApiMessages.APPLICATION_RUNNING,
                        statusService.getStatus()
                )
        );
    }

}
