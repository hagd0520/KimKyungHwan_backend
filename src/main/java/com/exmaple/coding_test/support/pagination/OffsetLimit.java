package com.exmaple.coding_test.support.pagination;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@RequiredArgsConstructor
public class OffsetLimit {
    private final int offset;
    private final int limit;

    public static OffsetLimit of(int offset, int limit) {
        return new OffsetLimit(offset, limit);
    }

    public Pageable toPageable() {
        return PageRequest.of(offset / limit, limit);
    }
}
