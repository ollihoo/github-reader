package de.ollihoo.service

import org.springframework.web.client.HttpClientErrorException

class JsonHelperException extends Exception {

    private String uri

    JsonHelperException(HttpClientErrorException httpClientErrorException, String uri) {
        super(httpClientErrorException)
        this.uri = uri
    }

    public String getRequest() {
        uri
    }
}
