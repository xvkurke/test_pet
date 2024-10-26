package dev.lynxie.webapi.tracker.controller;

import dev.lynxie.webapi.config.ControllerRoutes;
import dev.lynxie.webapi.master.controller.BaseController;
import dev.lynxie.webapi.master.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ReportController extends BaseController {

    @GetMapping(ControllerRoutes.REPORTS_MAIN)
    public ResponseEntity<Response> reportMain() {
        log.info("GET {}", ControllerRoutes.REPORTS_MAIN);
        return this.response(List.of(
                ControllerRoutes.REPORTS_GET_SUMMARY,
                ControllerRoutes.REPORTS_GET_MONTHLY,
                ControllerRoutes.REPORTS_GET_CATEGORY
        ));
    }

    @GetMapping(ControllerRoutes.REPORTS_GET_SUMMARY)
    public ResponseEntity<Response> getSummaryReport() {
        log.info("GET {}", ControllerRoutes.REPORTS_GET_SUMMARY);
        return this.response(ControllerRoutes.REPORTS_GET_SUMMARY);
    }

    @GetMapping(ControllerRoutes.REPORTS_GET_MONTHLY)
    public ResponseEntity<Response> getMonthlyReport() {
        log.info("GET {}", ControllerRoutes.REPORTS_GET_MONTHLY);
        return this.response(ControllerRoutes.REPORTS_GET_MONTHLY);
    }

    @GetMapping(ControllerRoutes.REPORTS_GET_CATEGORY)
    public ResponseEntity<Response> getCategoryReport() {
        log.info("GET {}", ControllerRoutes.REPORTS_GET_CATEGORY);
        return this.response(ControllerRoutes.REPORTS_GET_CATEGORY);
    }
}
