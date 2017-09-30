//////////////////////////////////////////////////////////////////////////
//                                                                      //
//      2017-09-20 - Created by Renco Steenbergen                       //
//                                                                      //
//////////////////////////////////////////////////////////////////////////

package com.rencomusic.apm.web;

import com.rencomusic.apm.persistence.Recipient;
import com.rencomusic.apm.persistence.RecipientService;
import com.rencomusic.apm.xlsx.XlsxReader;
import org.apache.poi.util.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/recipient")
public class RecipientRest {
    @Inject
    RecipientService recipientService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRecipient(Recipient recipient) {
        Recipient result = recipientService.createRecipient(recipient.getName(), recipient.getMail(), recipient.getCategory());
        String x = "Creating: " + result.getId() + " " + result.getName() + " " + result.getMail() + " " + result.getCategory();
        return Response.status(200).entity(x).build();
    }

    @GET
    @Path("/findall")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Recipient> result = recipientService.findAll();
        return Response.status(200).entity(result).build();
    }


    private final String UPLOADED_FILE_PATH = "";

    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    public Response uploadFile(MultipartFormDataInput input) {

        String fileName = "";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);

                InputStream inputStream = inputPart.getBody(InputStream.class,null);

                byte [] bytes = IOUtils.toByteArray(inputStream);

                fileName = UPLOADED_FILE_PATH + fileName;

                writeFile(bytes,fileName);

                importXlsx(fileName);

                Runtime.getRuntime().exec("rm -rf " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return Response.status(200)
                .entity("File uploaded: " + fileName).build();

    }

    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    private void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

    }

    private void importXlsx(String filename) {
        System.out.println(filename);
        try {
            ArrayList<String[]> matrix =
                    XlsxReader.readLines(new File("/opt/wildfly/" + filename));
            for(int i = 0; i < matrix.size(); i++) {
                if (recipientService.findByMail(matrix.get(i)[1].toLowerCase()).size() == 0) {
                    System.out.println(matrix.get(i)[0] +" " + matrix.get(i)[1] + " "+ matrix.get(i)[2]);
                    recipientService.createRecipient(matrix.get(i)[0], matrix.get(i)[1], matrix.get(i)[2]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
