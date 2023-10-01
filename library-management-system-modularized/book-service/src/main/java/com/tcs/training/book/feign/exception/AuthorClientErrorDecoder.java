package com.tcs.training.book.feign.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.book.exception.NoDataFoundException;
import com.tcs.training.book.model.exception.Problem;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class AuthorClientErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        Problem message = null;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, Problem.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        switch (response.status()) {
            case 400:
                return new NoDataFoundException(message.getDetail() != null ? message.getDetail() : "Bad Request");
            case 404:
                return new NoDataFoundException(message.getDetail() != null ? message.getDetail() : "Not found");
            default:
                return errorDecoder.decode(s, response);
        }
    }

}
