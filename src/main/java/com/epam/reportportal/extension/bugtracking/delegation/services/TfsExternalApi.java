package com.epam.reportportal.extension.bugtracking.delegation.services;

import com.epam.ta.reportportal.database.entity.ExternalSystem;
import com.epam.ta.reportportal.ws.model.externalsystem.AllowedValue;
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

import java.util.*;

@Service
public class TfsExternalApi implements IExternalSystemApi {
    private Map<String, Ticket> ticketStore = new HashMap<>();
    private int id = 0;

    @Value("rp.bts.tfs.service_url")
    private String microServiceUrl;
    @Value("rp.bts.tfs.server_url")
    private String tfsServerUrl;
    @Value("rp.bts.tfs.project")
    private String tfsProject;


    public List<String> getIssueTypes(ExternalSystem system) {
        RestTemplate template = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(microServiceUrl + "/api/issuetypes")
                .queryParam("uri", tfsServerUrl);

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
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(microServiceUrl + "/api/ticketfields/")
                .queryParam("uri", tfsServerUrl)
                .queryParam("project", tfsProject);

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
        Ticket t = new Ticket();
        id++;
        t.setId(Integer.toString(id));
        t.setStatus("Created");
        t.setTicketUrl("urL:" + id);
        t.setSummary(ticketRQ.toString());
        ticketStore.put(t.getId(), t);
        return t;
    }

    @Override
    public Optional<Ticket> getTicket(String id, ExternalSystem system) {
        RestTemplate template = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(microServiceUrl + "/api/ticket/" + id)
                .queryParam("uri", tfsServerUrl)
                .queryParam("project", tfsProject);

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
        return system.getUsername().equals("admin") && system.getPassword().equals("password");
    }
}
