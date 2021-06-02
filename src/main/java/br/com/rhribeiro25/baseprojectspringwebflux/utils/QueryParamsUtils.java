package br.com.rhribeiro25.baseprojectspringwebflux.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Class Query Params Utils
 *
 * @author Renan Henrique Ribeiro
 * @since 01/03/2021
 */
@Slf4j
public abstract class QueryParamsUtils {
    public static MultiValueMap<String, String> buildParamList(Long[] arrayInput, String nameValueUrl) {
        MultiValueMap<String, String> output = new LinkedMultiValueMap<>();
        if(arrayInput == null || arrayInput.length == 0)
            return output;
        for (Long value : arrayInput) {
            output.add(nameValueUrl, value.toString());
        }
        return output;
    }
}
