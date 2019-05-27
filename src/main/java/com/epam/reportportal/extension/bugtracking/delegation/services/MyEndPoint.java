package com.epam.reportportal.extension.bugtracking.delegation.services;

import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;
import com.epam.ta.reportportal.ws.model.externalsystem.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.GET;
import java.util.List;

@RestController
public class MyEndPoint {
    @Value("rp.bts.tfs.service.url")
    private String microServiceUrl;
    @Value("rp.bts.tfs.server.url")
    private String tfsServerUrl;
    @Value("rp.bts.tfs.project")
    private String tfsProject;

    public MyEndPoint(){
        System.out.println(1 + ": '" + microServiceUrl + "'");
        System.out.println(2 + ": '" + tfsServerUrl + "'");
        System.out.println(3 + ": '" + tfsProject + "'");
    }

    @RequestMapping("/test/{id}")
    public Ticket hello(@PathVariable String id){
        RestTemplate tmpl = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(microServiceUrl + "/api/ticket/" + id)
                .queryParam("uri", tfsServerUrl)
                .queryParam("project", tfsProject);
        return tmpl.getForObject(builder.toUriString(), Ticket.class);
    }

    @RequestMapping("/test")
    public List<String> hello(){
        RestTemplate tmpl = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(microServiceUrl + "/api/issuetypes")
                .queryParam("uri", tfsServerUrl)
                .queryParam("project", tfsProject);
        ResponseEntity<List<String>> response = tmpl.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        return response.getBody() ;
    }

    @RequestMapping("/test/f")
    public List<PostFormField> hello2(){
        RestTemplate tmpl = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(microServiceUrl + "/api/ticketfields")
                .queryParam("uri", tfsServerUrl)
                .queryParam("project", tfsProject);
        ResponseEntity<List<PostFormField>> response = tmpl.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<PostFormField>>() {});
        return response.getBody() ;
    }
}
