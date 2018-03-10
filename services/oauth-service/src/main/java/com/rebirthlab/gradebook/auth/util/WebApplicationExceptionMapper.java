package com.rebirthlab.gradebook.auth.util;

import java.time.Instant;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by Anastasiy
 */
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        Response response = exception.getResponse();
        ResponseMessage message = new ResponseMessage(Instant.now(),
                response.getStatus(),
                response.getStatusInfo().getReasonPhrase(),
                exception.getMessage());

        return Response.fromResponse(response)
                .entity(message)
                .build();
    }
}
