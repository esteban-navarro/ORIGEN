package cl.origen.platform.modules.health.service;

import org.springframework.stereotype.Service;

import cl.origen.platform.config.properties.ApplicationProperties;
import cl.origen.platform.modules.health.dto.StatusResponse;
import lombok.RequiredArgsConstructor;

/**
 * Service responsible for providing application status information.
 */
@Service
@RequiredArgsConstructor
public class StatusService {

    private final ApplicationProperties applicationProperties;

    /**
     * Builds the application status response.
     *
     * @return application metadata.
     */
    public StatusResponse getStatus() {

        return StatusResponse.builder()
                .application(applicationProperties.getName())
                .version(applicationProperties.getVersion())
                .description(applicationProperties.getDescription())
                .build();
    }

}
