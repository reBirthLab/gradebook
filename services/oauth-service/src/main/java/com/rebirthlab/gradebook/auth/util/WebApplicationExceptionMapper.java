package com.rebirthlab.gradebook.auth.util;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by Anastasiy
 */
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(WebApplicationException exception) {
        Response response = exception.getResponse();
        ResponseMessage message = new ResponseMessage(Instant.now(),
                response.getStatus(),
                response.getStatusInfo().getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());

        return Response.fromResponse(response)
                .entity(message)
                .build();
    }
}
