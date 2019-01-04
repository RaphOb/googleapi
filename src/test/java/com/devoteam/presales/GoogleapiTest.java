package com.devoteam.presales;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.calendar.Calendar;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.devoteam.presales.Googleapi.*;
import static org.junit.Assert.assertEquals;

public class GoogleapiTest {

    @Test
    public void checkIdEventTest() throws IOException, GeneralSecurityException {
    Googleapi googleapi = new Googleapi();
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Calendar service = new Calendar.Builder(HTTP_TRANSPORT,googleapi.JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(googleapi.APPLICATION_NAME)
            .build();

    assertEquals(("7mqnv8oe6vtultibnsqv8kdvt4_20190218"),checkIdevent(service, "bor"));
}
    @Test
    public void deleteEventTest()throws IOException, GeneralSecurityException
    {
        Googleapi googleapi = new Googleapi();
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT,googleapi.JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(googleapi.APPLICATION_NAME)
                .build();
       // assertEquals((checkIdevent(service, "bor")),(deleteEvent(service,"primary", checkIdevent(service,"bor"))));

    }

    @Test
    public void addEventTest()throws IOException, GeneralSecurityException
    {
        Googleapi googleapi = new Googleapi();
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT,googleapi.JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(googleapi.APPLICATION_NAME)
                .build();
    }

    @Test
    public void patchEventTest()throws IOException, GeneralSecurityException
    {
        Googleapi googleapi = new Googleapi();
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT,googleapi.JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(googleapi.APPLICATION_NAME)
                .build();
    }
}
