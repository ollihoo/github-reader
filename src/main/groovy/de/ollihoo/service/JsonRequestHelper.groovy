package de.ollihoo.service

import groovy.json.JsonSlurper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Component
class JsonRequestHelper {

    public getAndParseJson(URI jsonUri) {
        new JsonSlurper().parseText(doRequestOn(jsonUri))
    }

    public String doRequestOn(URI uri) throws HttpClientErrorException {
        RestTemplate restTemplate = new RestTemplate()
        HttpHeaders headers = new HttpHeaders()
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers)

        ResponseEntity response = restTemplate.exchange(uri, HttpMethod.GET, entity, String)
        response.getBody()
    }

}
