package com.dawid.documenttendency.util;

import com.dawid.documenttendency.exception.DocumentException;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


import static com.dawid.documenttendency.exception.DocumentError.DATE_END_IS_BEFORE_DATE_START;
import static com.dawid.documenttendency.exception.DocumentError.DATE_HAS_NO_VALID_FORMAT;


@Service
public class InputParser {


    public LocalDate parseDate(String dateString) {

        validateInputFormat(dateString);
        LocalDate date = LocalDate.parse(dateString);

        return date;
    }

    private void validatePeriod(LocalDate fromDate, LocalDate toDate) {
        if (toDate.isBefore(fromDate)) {
            throw new DocumentException(DATE_END_IS_BEFORE_DATE_START);
        }
    }

    private void validateInputFormat(String DateString) {
        if (!hasDateValidFormat(DateString)) {
            throw new DocumentException(DATE_HAS_NO_VALID_FORMAT);
        }
    }

    private boolean hasDateValidFormat(String date) {

        return (GenericValidator.isDate(date, Properties.DATA_FORMAT_PATTERN, true));
    }
}
