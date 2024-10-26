package dev.lynxie.webapi.master.exception.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Slf4j
public class RequestResponseLogFilter extends OncePerRequestFilter {

    public static final String ID_HEADER = "Request-Id";
    private static final String ACTUATOR_MARKER = "actuat";
    private static final String LOG_FIELD_PATTERN = "%s:%s";
    private static final Set<String> HEALTH_PROBE_MARKER = Set.of("/", "");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isIgnoreLogging(request.getRequestURI())) {
            return;
        }

        final UUID requestId = UUID.randomUUID();
        fillRequestInHeader(response, requestId);
        logRequest(request, requestId);

        filterChain.doFilter(request, response);

        logResponse(response, requestId);
    }

    private void fillRequestInHeader(HttpServletResponse response, UUID requestId) {
        response.addHeader(ID_HEADER, requestId.toString());
    }

    private boolean isIgnoreLogging(String url) {
        return Objects.nonNull(url) && (HEALTH_PROBE_MARKER.contains(url)) || url.contains(ACTUATOR_MARKER);
    }

    private void logRequest(HttpServletRequest request, UUID requestId) {
        log.info("""
                        \n--------------- START Request {}
                        method - {}
                        requestURL - {}
                        queryString - {}
                        ip - {}
                        headers - {}
                        cookies - {}""",
                requestId,
                request.getMethod(),
                request.getRequestURL(),
                request.getQueryString() == null ? "" : request.getQueryString(),
                request.getRemoteAddr(),
                getRequestHeaders(request),
                getRequestCookies(request));
    }

    private String getRequestHeaders(HttpServletRequest request) {
        return StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(request.getHeaderNames().asIterator(), Spliterator.ORDERED),
                        false
                )
                .map(key -> String.format(LOG_FIELD_PATTERN, key, request.getHeader(key)))
                .collect(Collectors.joining(", "));
    }

    private String getResponseHeaders(HttpServletResponse response) {
        return response.getHeaderNames()
                .stream()
                .map(header -> String.format(LOG_FIELD_PATTERN, header, response.getHeader(header)))
                .collect(Collectors.joining(", "));
    }

    private String getRequestCookies(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .map(cookie -> String.format(LOG_FIELD_PATTERN, cookie.getName(), cookie.getValue()))
                .collect(Collectors.joining(", "));
    }

    private void logResponse(HttpServletResponse response, UUID requestId) {
        log.info("""
                        status - {}
                        headers - {}
                        --------------- FINISH Request {}""",
                response.getStatus(),
                getResponseHeaders(response),
                requestId);
    }
}
