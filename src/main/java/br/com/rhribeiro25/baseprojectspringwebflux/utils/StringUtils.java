package br.com.rhribeiro25.baseprojectspringwebflux.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Class Object Utils
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Slf4j
public class StringUtils {
    public final static Boolean isNullOrBlank(String value) {
        return (value == null || value.isBlank());
    }

    public final static Boolean isNotNullAndBlank(String value) {
        return (value != null && !value.isBlank());
    }
}
