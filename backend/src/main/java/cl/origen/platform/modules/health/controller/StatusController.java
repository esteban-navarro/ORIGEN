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
import lombok.RequiredArgsConstructor;

/**
 * REST controller that exposes application health endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StatusController {

    private final StatusService statusService;

    /**
     * Returns application status information.
     *
     * @return application status response
     */
    @GetMapping("/status")
    public ResponseEntity<Response<StatusResponse>> getStatus() {

        return ResponseEntity.ok(
                ResponseFactory.ok(
                        statusService.getStatus(),
                        ApiMessages.APPLICATION_RUNNING
                )
        );
    }

}
