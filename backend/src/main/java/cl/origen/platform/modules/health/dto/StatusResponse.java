package cl.origen.platform.modules.health.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Response payload returned by the health endpoint.
 */
@Getter
@Builder
public class StatusResponse {

    /**
     * Application name.
     */
    private final String application;

    /**
     * Current application version.
     */
    private final String version;

    /**
     * Short application description.
     */
    private final String description;

}
