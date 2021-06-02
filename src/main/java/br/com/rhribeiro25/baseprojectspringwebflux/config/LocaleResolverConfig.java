package br.com.rhribeiro25.baseprojectspringwebflux.config;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.LocaleContextResolver;

import java.util.List;
import java.util.Locale;

/**
 * Locale configuration class
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
public class LocaleResolverConfig implements LocaleContextResolver {

    @Override
    public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {
        Locale targetLocale = new Locale("pt", "BR");
        List<String> referLang = exchange.getRequest().getQueryParams().get("lang");
        if (referLang != null && !referLang.isEmpty()) {
            String lang = referLang.get(0);
            targetLocale = Locale.forLanguageTag(lang);
        }

        return new SimpleLocaleContext(targetLocale);
    }

    @Override
    public void setLocaleContext(ServerWebExchange exchange, LocaleContext localeContext) {
        throw new UnsupportedOperationException("Not Supported");
    }
}