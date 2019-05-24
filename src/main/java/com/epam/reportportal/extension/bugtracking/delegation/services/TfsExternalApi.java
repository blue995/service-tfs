package com.epam.reportportal.extension.bugtracking.delegation.services;

import com.epam.ta.reportportal.database.entity.ExternalSystem;
import com.epam.ta.reportportal.ws.model.externalsystem.AllowedValue;
import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;
import com.epam.ta.reportportal.ws.model.externalsystem.PostTicketRQ;
import com.epam.ta.reportportal.ws.model.externalsystem.Ticket;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TfsExternalApi implements IExternalSystemApi{
    private Map<String, Ticket> ticketStore = new HashMap<>();
    private int id = 0;


    public List<String> getIssueTypes(ExternalSystem system) {
        List<String> issueTypes = new LinkedList<>();
        issueTypes.add("Defect");
        issueTypes.add("User Story");
        issueTypes.add("Work Item");
        return issueTypes;
    }

    @Override
    public List<PostFormField> getTicketFields(String issueType, ExternalSystem system) {
        List<PostFormField> fields = new LinkedList<>();
        PostFormField field = new PostFormField();
        field.setId("desc");
        field.setFieldName("Description");
        field.setFieldType("text");
        field.setIsRequired(true);
        fields.add(field);

        field = new PostFormField();
        field.setId("created");
        field.setFieldName("Created by");
        field.setFieldType("text");
        field.setIsRequired(true);
        fields.add(field);

        field = new PostFormField();
        field.setId("age");
        field.setFieldName("Age");
        field.setFieldType("Double");
        field.setIsRequired(true);
        List<AllowedValue> values = new LinkedList<>();
        values.add(new AllowedValue("1", "33.1"));
        values.add(new AllowedValue("2", "33.2"));
        values.add(new AllowedValue("3", "33.3"));
        values.add(new AllowedValue("4", "33.4"));
        field.setDefinedValues(values);
        fields.add(field);

        field = new PostFormField();
        field.setId("age1");
        field.setFieldName("Age1");
        field.setFieldType("Double");
        field.setIsRequired(true);
        List<String> values1 = new LinkedList<>();
        values1.add("1");
        values1.add("2");
        values1.add("3");
        values1.add("4");
        field.setValue(values1);
        fields.add(field);

        field = new PostFormField();
        field.setId("combi");
        field.setFieldName("Age");
        field.setFieldType("Double");
        field.setIsRequired(true);
        values = new LinkedList<>();
        values.add(new AllowedValue("1", "33.1"));
        values.add(new AllowedValue("2", "33.2"));
        values.add(new AllowedValue("3", "33.3"));
        values.add(new AllowedValue("4", "33.4"));
        field.setDefinedValues(values);
        values1 = new LinkedList<>();
        values1.add("1");
        values1.add("2");
        values1.add("3");
        values1.add("4");
        field.setValue(values1);
        fields.add(field);

        return fields;
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
        if (ticketStore.containsKey(id)) {
            return Optional.of(ticketStore.get(id));
        }
        return Optional.empty();
    }

    @Override
    public boolean checkConnection(ExternalSystem system) {
        return system.getUsername().equals("admin") && system.getPassword().equals("password");
    }
}
