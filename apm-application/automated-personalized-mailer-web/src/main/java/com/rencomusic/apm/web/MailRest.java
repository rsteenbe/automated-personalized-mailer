package com.rencomusic.apm.web;

import com.rencomusic.apm.mail.MailAgent;
import com.rencomusic.apm.persistence.Mail;
import com.rencomusic.apm.persistence.MailService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/mail")
public class MailRest {
    @Inject
    MailAgent mailAgent;

    @Inject
    MailService mailService;

    @POST
    @Path("/message")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response composeMail(Mail mail) {
        System.out.println("Mail subject: " + mail.getSubject());
        try {
            mailAgent.sendGroup(mail);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Response.status(200).build();
    }

    @GET
    @Path("/recipients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllGroupMail() {
        List<Mail> result = mailService.findAllMail();
        return Response.status(200).entity(result).build();
    }
}