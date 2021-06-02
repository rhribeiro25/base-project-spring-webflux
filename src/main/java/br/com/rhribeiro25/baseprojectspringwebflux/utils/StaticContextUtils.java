package br.com.rhribeiro25.baseprojectspringwebflux.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Class Static Context Utils
 *
 * @author Renan Henrique Ribeiro
 * @since 01/03/2021
 */

@Component
public class StaticContextUtils {

    private static StaticContextUtils instance;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void registerInstance() {
        instance = this;
    }

    public static <T> T getBean(Class<T> clazz) {
        return instance.applicationContext.getBean(clazz);
    }

}
