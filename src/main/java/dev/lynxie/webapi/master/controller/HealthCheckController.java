package dev.lynxie.webapi.master.controller;

import dev.lynxie.webapi.config.ControllerRoutes;
import dev.lynxie.webapi.master.dto.Response;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController extends BaseController {

    @GetMapping(ControllerRoutes.HEALTH_CHECK)
    public ResponseEntity<Response> healthCheck() {
        return this.response(new UpResponseDto());
    }

    @Data
    private static class UpResponseDto {
        private String status;

        private UpResponseDto() {
            setStatus("UP");
        }
    }
}
