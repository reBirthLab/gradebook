package com.rebirthlab.gradebook.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

/**
 * The interceptor is created for conversion of 302 redirect responses into 200 OK.
 * It was introduced to satisfy current client app implementation that doesn't use redirect flow
 * and expects authorization token in response to provided user credentials.
 * <p>
 * Created by Anastasiy
 */
public class RedirectResponseHandler extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && RedirectView.class.isAssignableFrom(modelAndView.getView().getClass())) {
            RedirectView redirectView = (RedirectView) modelAndView.getView();
            response.addHeader("X-Location", redirectView.getUrl());
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
            modelAndView.clear();
        }
    }

}
