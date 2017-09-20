//////////////////////////////////////////////////////////////////////////
//                                                                      //
//      2017-09-20 - Created by Renco Steenbergen                       //
//                                                                      //
//////////////////////////////////////////////////////////////////////////

package com.rencomusic.apm.web;

import com.rencomusic.apm.persistence.Recipient;
import com.rencomusic.apm.persistence.RecipientService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/recipient")
public class RecipientRest {
    @Inject
    RecipientService recipientService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRecipient(Recipient recipient) {
        Recipient result = recipientService.createArtist(recipient.getName(), recipient.getMail(), recipient.getCategory());
        String x = "" + result.getId() + " " + result.getName() + " " + result.getMail() + " " + result.getCategory();
        System.out.println(x);
        return Response.status(200).entity(x).build();
    }

    @GET
    @Path("/findall")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Recipient> result = recipientService.findAll();
        return Response.status(200).entity(result).build();
    }
}
