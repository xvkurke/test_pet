package dev.lynxie.webapi.tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dev.lynxie.webapi.config.ControllerRoutes;
import dev.lynxie.webapi.master.controller.BaseController;
import dev.lynxie.webapi.master.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CurrencyController extends BaseController {

    private static final String NBU_EXCHANGE_API_ROUTE = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @GetMapping(ControllerRoutes.CURRENCY_LIST)
    public ResponseEntity<Response> currencyList() {
        log.info("GET {}", ControllerRoutes.CURRENCY_LIST);
        return this.response(getExchangeResponse());
    }

    @GetMapping(ControllerRoutes.EXCHANGE_RATES)
    public ResponseEntity<Response> exchangeRates() {
        log.info("GET {}", ControllerRoutes.EXCHANGE_RATES);
        return this.response(ControllerRoutes.EXCHANGE_RATES);
    }

    private String getExchangeResponse() {
        try {
            URL obj = new URL(NBU_EXCHANGE_API_ROUTE);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return getPrettyJson(response.toString());
            } else {
                log.error("GET request not worked");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return null;
    }

    private String getPrettyJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(json, Object.class);
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(jsonObject);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
