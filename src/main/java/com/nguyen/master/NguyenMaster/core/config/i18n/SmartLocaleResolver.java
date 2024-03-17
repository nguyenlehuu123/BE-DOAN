package com.nguyen.master.NguyenMaster.core.config.i18n;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

public class SmartLocaleResolver extends CookieLocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String acceptLanguage = request.getHeader("Accept-Language");
        if (acceptLanguage == null || acceptLanguage.trim().isEmpty()) {
            return super.determineDefaultLocale(request);
        }
        return request.getLocale();
    }
}
