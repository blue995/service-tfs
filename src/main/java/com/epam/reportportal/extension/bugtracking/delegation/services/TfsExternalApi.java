package com.epam.reportportal.extension.bugtracking.delegation.services;

import com.epam.ta.reportportal.database.entity.ExternalSystem;
import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;
import com.epam.ta.reportportal.ws.model.externalsystem.PostTicketRQ;
import com.epam.ta.reportportal.ws.model.externalsystem.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TfsExternalApi implements IExternalSystemApi {
    @Value("${rp.bts.tfs.service.url}")
    private String microServiceUrl;

    public List<String> getIssueTypes(ExternalSystem system) {
        RestTemplate template = new RestTemplate();
        String path = microServiceUrl + "/api/issuetypes";
        UriComponentsBuilder builder = getUriBuilder(path, system);

        try {
            ResponseEntity<List<String>> response = template.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
            return response.getBody() ;
        } catch (RestClientException e){
            e.printStackTrace();
            System.out.println("Message: "+ e.getMessage());
            return new LinkedList<>();
        }
    }

    @Override
    public List<PostFormField> getTicketFields(String issueType, ExternalSystem system) {
        RestTemplate template = new RestTemplate();
        String path = microServiceUrl + "/api/ticketfields";
        UriComponentsBuilder builder = getUriBuilder(path, system);
        builder.queryParam("type", issueType);

        try {
            ResponseEntity<List<PostFormField>> response = template.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<PostFormField>>() {});
            return response.getBody() ;
        } catch (RestClientException e){
            e.printStackTrace();
            System.out.println("Message: "+ e.getMessage());
            return new LinkedList<>();
        }
    }

    @Override
    public Ticket submitTicket(PostTicketRQ ticketRQ, ExternalSystem system) {
        RestTemplate template = new RestTemplate();
        String path = microServiceUrl + "/api/ticket";
        UriComponentsBuilder builder = getUriBuilder(path, system);

        return template.postForObject(builder.toUriString(), ticketRQ, Ticket.class);
    }

    @Override
    public Optional<Ticket> getTicket(String id, ExternalSystem system) {
        RestTemplate template = new RestTemplate();
        String path = microServiceUrl + "/api/ticket/" + id;
        UriComponentsBuilder builder = getUriBuilder(path, system);

        try {
            return Optional.of(template.getForObject(builder.toUriString(), Ticket.class));
        } catch (RestClientException e){
            e.printStackTrace();
            System.out.println("Message: "+ e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean checkConnection(ExternalSystem system) {
        RestTemplate template = new RestTemplate();
        String path = microServiceUrl + "/api/welcome";
        UriComponentsBuilder builder = getUriBuilder(path, system);
        return template.getForObject(builder.toUriString(), Boolean.class);
    }

    private static UriComponentsBuilder getUriBuilder(String path, ExternalSystem system){
        return UriComponentsBuilder
                .fromUriString(path)
                .queryParam("uri", system.getUrl())
                .queryParam("project", system.getProject());
    }
}
