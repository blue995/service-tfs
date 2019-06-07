package com.epam.reportportal.extension.bugtracking.delegation.services;

import com.epam.reportportal.extension.bugtracking.ExternalSystemStrategy;
import com.epam.ta.reportportal.database.entity.ExternalSystem;
import com.epam.ta.reportportal.ws.model.externalsystem.AllowedValue;
import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;
import com.epam.ta.reportportal.ws.model.externalsystem.PostTicketRQ;
import com.epam.ta.reportportal.ws.model.externalsystem.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DelegationStrategy implements ExternalSystemStrategy {
    private IExternalSystemApi myApi;

    @Autowired
    public DelegationStrategy(IExternalSystemApi api){
        this.myApi = api;
    }

    @Override
    public boolean checkConnection(ExternalSystem system) {
        return myApi.checkConnection(system);
    }

    @Override
    public Optional<Ticket> getTicket(String id, ExternalSystem system) {
        return myApi.getTicket(id, system);
    }

    @Override
    public Ticket submitTicket(PostTicketRQ ticketRQ, ExternalSystem system) {
        return myApi.submitTicket(ticketRQ, system);
    }

    @Override
    public List<PostFormField> getTicketFields(String issueType, ExternalSystem system) {
        return myApi.getTicketFields(issueType, system);
    }

    @Override
    public List<String> getIssueTypes(ExternalSystem system) {
        return myApi.getIssueTypes(system);
    }
}
