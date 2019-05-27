package com.epam.reportportal.extension.bugtracking.delegation.services;

import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;
import com.epam.ta.reportportal.ws.model.externalsystem.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private Environment env;

    @RequestMapping("/test/{id}")
    public Ticket hello(@PathVariable String id){
        RestTemplate tmpl = new RestTemplate();
        String service_url = env.getProperty("rp.bts.tfs.service_url");
        String tfs_url = env.getProperty("rp.bts.tfs.server_url");
        String project = env.getProperty("rp.bts.tfs.project");

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(service_url + "/api/ticket/" + id)
                .queryParam("uri", tfs_url)
                .queryParam("project", project);
        return tmpl.getForObject(builder.toUriString(), Ticket.class);
    }

    @RequestMapping("/test")
    public List<String> hello(){
        RestTemplate tmpl = new RestTemplate();
        String service_url = env.getProperty("rp.bts.tfs.service_url");
        String tfs_url = env.getProperty("rp.bts.tfs.server_url");
        String project = env.getProperty("rp.bts.tfs.project");

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(service_url + "/api/issuetypes")
                .queryParam("uri", tfs_url)
                .queryParam("project", project);
        ResponseEntity<List<String>> response = tmpl.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        return response.getBody() ;
    }

    @RequestMapping("/test/f")
    public List<PostFormField> hello2(){
        RestTemplate tmpl = new RestTemplate();
        String service_url = env.getProperty("rp.bts.tfs.service_url");
        String tfs_url = env.getProperty("rp.bts.tfs.server_url");
        String project = env.getProperty("rp.bts.tfs.project");

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(service_url + "/api/ticketfields")
                .queryParam("uri", tfs_url)
                .queryParam("project", project);
        ResponseEntity<List<PostFormField>> response = tmpl.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<PostFormField>>() {});
        return response.getBody() ;
    }
}
