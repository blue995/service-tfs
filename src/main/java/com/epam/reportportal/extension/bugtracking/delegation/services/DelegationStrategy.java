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
        System.out.println("Check Connection");
        printSystem(system);
        return myApi.checkConnection(system);
    }

    @Override
    public Optional<Ticket> getTicket(String id, ExternalSystem system) {
        System.out.println("Get Ticket");
        return myApi.getTicket(id, system);
    }

    @Override
    public Ticket submitTicket(PostTicketRQ ticketRQ, ExternalSystem system) {
        System.out.println("Submit Ticket: " + ticketRQ);
        return myApi.submitTicket(ticketRQ, system);
    }

    @Override
    public List<PostFormField> getTicketFields(String issueType, ExternalSystem system) {
        System.out.println("Get Ticket fields");
        return myApi.getTicketFields(issueType, system);
    }

    @Override
    public List<String> getIssueTypes(ExternalSystem system) {
        System.out.println("Get issue types");
        return myApi.getIssueTypes(system);
    }

    private void printSystem(ExternalSystem system) {
        StringBuilder builder = new StringBuilder();
        builder.append("Access key: ");
        builder.append(system.getAccessKey());
        builder.append("\nDomain: ");
        builder.append(system.getDomain());
        builder.append("\nSystem type: ");
        builder.append(system.getExternalSystemType());
        builder.append("\nSystem auth: ");
        builder.append(system.getExternalSystemAuth());
        builder.append("\nSystem id: ");
        builder.append(system.getId());
        builder.append("\nPassword: ");
        builder.append(system.getPassword());
        builder.append("\nUsername: ");
        builder.append(system.getUsername());
        builder.append("\nSystem url: ");
        builder.append(system.getUrl());
        builder.append("\nProject: ");
        builder.append(system.getProject());
        builder.append("\nProject ref: ");
        builder.append(system.getProjectRef());
        if(system.getFields() != null){
            for(PostFormField f : system.getFields()){
                builder.append("\nField: ");
                builder.append(f.getFieldName());
                builder.append(" | ");
                builder.append(f.getFieldType());
                if (f.getDefinedValues() == null){
                    continue;
                }
                for (AllowedValue v: f.getDefinedValues()){
                    builder.append("=> (");
                    builder.append(v.getValueName());
                    builder.append(" ");
                    builder.append(v.getValueId());
                    builder.append(")");
                }
            }
        }
        System.out.println("System: " + builder.toString());
    }
}
