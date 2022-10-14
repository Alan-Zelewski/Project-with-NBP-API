package com.azelewski.exercise.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateExceptionHandler implements ResponseErrorHandler {
    private static final Logger LOGGER = LogManager.getLogger(RestTemplateExceptionHandler.class);
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
        || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if(response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR){
            LOGGER.error(response.getStatusCode());
        } else if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            LOGGER.error(response.getStatusCode());
            if(response.getStatusCode() == HttpStatus.NOT_FOUND){
                LOGGER.error(response.getStatusCode());
            }
        }
    }
}
