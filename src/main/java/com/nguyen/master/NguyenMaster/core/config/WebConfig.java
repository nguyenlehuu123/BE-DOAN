package com.nguyen.master.NguyenMaster.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class WebConfig {

    @Value("${system.timezone.id: UTC}")
    private String timezoneId;

    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(timezoneId));
    }
}
