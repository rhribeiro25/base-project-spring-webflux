package br.com.rhribeiro25.baseprojectspringwebflux.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.server.i18n.LocaleContextResolver;

/**
 * Locale configuration class
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Configuration
public class LocaleConfig extends DelegatingWebFluxConfiguration {

    @Override
    protected LocaleContextResolver createLocaleContextResolver() {
        return new LocaleResolverConfig();
    }
}
