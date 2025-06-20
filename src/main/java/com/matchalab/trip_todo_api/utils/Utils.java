package com.matchalab.trip_todo_api.utils;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Utils {

    public static URI getLocation(Object resourceId) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{childId}").buildAndExpand(resourceId)
                .toUri();
    }
}
