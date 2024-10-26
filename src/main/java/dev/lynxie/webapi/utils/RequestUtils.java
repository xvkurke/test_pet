package dev.lynxie.webapi.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
@Slf4j
@NoArgsConstructor
public class RequestUtils {

    public String extractBody(HttpServletRequest request) {
        if (HttpMethod.GET.name().equals(request.getMethod()) || HttpMethod.DELETE.name().equals(request.getMethod())) {
            return null;
        }

        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            try {
                return new String(wrapper.getContentAsByteArray(), request.getCharacterEncoding());
            } catch (Exception ignored) {
                log.error("Can not parse request body in advice adapter with encoding {}", request.getCharacterEncoding());
            }
            return null;
        }

        if (request instanceof SecurityContextHolderAwareRequestWrapper) {
            SecurityContextHolderAwareRequestWrapper wrapper = (SecurityContextHolderAwareRequestWrapper) request;
            try {
                return new String(wrapper.getRequest().getInputStream().readAllBytes(), request.getCharacterEncoding());
            } catch (Exception ignored) {
                log.error("Can not parse request body in advice adapter with encoding {}", request.getCharacterEncoding());
            }
            return null;
        }

        return null;
    }
}
