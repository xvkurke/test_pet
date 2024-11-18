package dev.lynxie.webapi.master.controller;

import lombok.RequiredArgsConstructor;
import dev.lynxie.webapi.config.ControllerRoutes;
import dev.lynxie.webapi.master.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController extends BaseController {

    @GetMapping(ControllerRoutes.HEALTH_CHECK)
    public ResponseEntity<Response> healthCheck() {
        boolean isHealthy = checkApplicationHealth();
        return this.response(isHealthy ? new HealthStatus("UP") : new HealthStatus("DOWN"));
    }

    private boolean checkApplicationHealth() {
        // Will be extended by customizable health check logic here (e.g., check DB connection, external services, etc.)
        // For simplicity, we're returning true, but you can make it more robust.
        return true;
    }

    private record HealthStatus(String status) { }
}
