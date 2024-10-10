package com.example.Reservation_app.Utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ReservationAppUtils {

    public static Pageable getPageMetadata(Integer page, Integer pageSize, String sortBy, String sortDir){
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        return  PageRequest.of(page, pageSize, sort);
    }
}
