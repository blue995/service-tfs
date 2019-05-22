package com.epam.reportportal.extension.bugtracking.delegation.services;

import com.epam.ta.reportportal.database.entity.ExternalSystem;
import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;
import com.epam.ta.reportportal.ws.model.externalsystem.PostTicketRQ;
import com.epam.ta.reportportal.ws.model.externalsystem.Ticket;

import java.util.List;
import java.util.Optional;

public interface IExternalSystemApi {
    List<String> getIssueTypes(ExternalSystem system);
    List<PostFormField> getTicketFields(String issueType, ExternalSystem system);
    Ticket submitTicket(PostTicketRQ ticketRQ, ExternalSystem system);
    Optional<Ticket> getTicket(String id, ExternalSystem system);
    boolean checkConnection(ExternalSystem system);
}
