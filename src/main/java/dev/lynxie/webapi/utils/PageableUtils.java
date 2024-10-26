package dev.lynxie.webapi.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageableUtils {

    public static final List<Sort.Order> USERS_ORDER_LIST = List.of(new Sort.Order(Sort.Direction.ASC, "email"));

    public static Pageable generatePageable(Integer page, Integer size, Sort.Direction sortDirection,
                                            String sortByField, String defaultSortBy) {
        String field = sortByField != null ? sortByField : defaultSortBy;
        int pageNumber = page != null ? page : 0;
        int pageSize = size != null ? size : 25;

        return generatePageable(pageNumber, pageSize, List.of(new Sort.Order(sortDirection, field)));
    }

    public static Pageable generatePageable(int pageNumber, int pageSize, List<Sort.Order> orders) {
        return PageRequest.of(pageNumber, pageSize, Sort.by(orders));
    }

    public static Long getTotalElement(Pageable pageable, Integer resultSize) {
        if (!pageable.isPaged()) {
            return 0L;
        }

        int pageSize = pageable.getPageSize();
        if (resultSize != pageSize) {
            return (long) pageSize * pageable.getPageNumber() + resultSize;
        } else {
            return (pageable.getOffset() + 10) * pageSize;
        }
    }
}
