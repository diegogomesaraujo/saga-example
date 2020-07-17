package com.example.web.resources;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractResource {

    protected String urlBase(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

}
