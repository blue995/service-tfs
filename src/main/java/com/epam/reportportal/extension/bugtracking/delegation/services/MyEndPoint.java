package com.epam.reportportal.extension.bugtracking.delegation.services;

import com.epam.ta.reportportal.ws.model.externalsystem.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.GET;

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
}
