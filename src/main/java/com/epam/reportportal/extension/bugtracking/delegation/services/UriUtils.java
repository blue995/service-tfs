package com.epam.reportportal.extension.bugtracking.delegation.services;

import com.epam.ta.reportportal.database.entity.ExternalSystem;
import org.springframework.web.util.UriComponentsBuilder;

public class UriUtils {
    public static UriComponentsBuilder getUriBuilder(String path, ExternalSystem system) {
        return UriComponentsBuilder
                .fromUriString(path)
                .queryParam("uri", system.getUrl())
                .queryParam("project", system.getProject());
    }
}
