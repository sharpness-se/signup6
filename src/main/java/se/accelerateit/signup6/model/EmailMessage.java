package se.accelerateit.signup6.model;

import lombok.Data;

import java.net.URL;

@Data
public class EmailMessage {

    private String to;
    private String subject;
    private String message;
    private URL eventURL;
}
