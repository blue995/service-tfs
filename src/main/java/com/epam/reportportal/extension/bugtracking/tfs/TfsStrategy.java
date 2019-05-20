package com.epam.reportportal.extension.bugtracking.tfs;

import com.epam.reportportal.extension.bugtracking.ExternalSystemStrategy;
import com.epam.ta.reportportal.database.entity.ExternalSystem;
import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;
import com.epam.ta.reportportal.ws.model.externalsystem.PostTicketRQ;
import com.epam.ta.reportportal.ws.model.externalsystem.Ticket;

import java.util.List;
import java.util.Optional;

public class TfsStrategy implements ExternalSystemStrategy {
    @Override
    public boolean checkConnection(ExternalSystem system) {
        System.out.println("Check Connection");
        return false;
    }

    @Override
    public Optional<Ticket> getTicket(String id, ExternalSystem system) {
        System.out.println("Get Ticket");
        return Optional.empty();
    }

    @Override
    public Ticket submitTicket(PostTicketRQ ticketRQ, ExternalSystem system) {
        System.out.println("Submit Ticket");
        return null;
    }

    @Override
    public List<PostFormField> getTicketFields(String issueType, ExternalSystem system) {
        System.out.println("Get Ticket fields");
        return null;
    }

    @Override
    public List<String> getIssueTypes(ExternalSystem system) {
        System.out.println("Get issue types");
        return null;
    }
}
