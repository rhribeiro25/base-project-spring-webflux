package br.com.rhribeiro25.baseprojectspringwebflux.utils;

import lombok.extern.log4j.Log4j2;

/**
 * Class Object Utils
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Log4j2
public class StringUtils {
    public final static Boolean isNullOrBlank(String value) {
        return (value == null || value.isBlank());
    }

    public final static Boolean isNotNullAndBlank(String value) {
        return (value != null && !value.isBlank());
    }
}
