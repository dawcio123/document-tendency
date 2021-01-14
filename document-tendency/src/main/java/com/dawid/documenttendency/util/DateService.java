package com.dawid.documenttendency.util;

import com.dawid.documenttendency.exception.DocumentException;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.dawid.documenttendency.exception.DocumentError.DATE_END_IS_BEFORE_DATE_START;
import static com.dawid.documenttendency.exception.DocumentError.DATE_HAS_NO_VALID_FORMAT;


@Service
public class DateService {



    public void validatePeriod(LocalDate fromDate, LocalDate toDate) {
        if (toDate.isBefore(fromDate)){
            throw new DocumentException(DATE_END_IS_BEFORE_DATE_START);
        }
    }

    public LocalDate getFirstDayOfPreviousWeek() {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        LocalDate BegOfThisWeek = LocalDate.now().minusDays(dayOfWeek - 1);
        LocalDate fromDate = BegOfThisWeek.minusWeeks(1);
        return fromDate;
    }


}
