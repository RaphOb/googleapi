package com.devoteam.presales;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Googleapi {
    protected static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "c:/temp/toup/tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    static java.io.File CREDENTIALS_FILE_PATH;

    public Googleapi(String path)
    {
        CREDENTIALS_FILE_PATH = new java.io.File(path);
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    protected static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        //  checkCal(service, 1);
        // addCalendar(service);
        //  checkIdevent(service, "bor");
        //  deleteEvent(service,"primary", checkIdevent(service,"bor"));
       // updEvent(service, "primary", checkIdevent(service,"bor"), patchEvent());
        //addEvent(service, newEvent());

    }

    /**
     * @function : checkIdevent
     * @return : String
     *
     * @param : Objet Calendar
     * @param : String, recherche par le nom.
     *
     * @details : Recheche un evenement .
     */
    public static String checkIdevent(String q) throws IOException, GeneralSecurityException
    {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        DateTime now = new DateTime(System.currentTimeMillis());
        String res = "null";
        Events events = service.events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setQ("cocou")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Event found");
            for (Event event : items) {
                res = event.getId();
            }
        }
        System.out.printf("%s", res);
        return res;
    }

    /**
     * @function : checkCal
     *
     * @param : Objet Calendar
     * @param : Integer, le nombre d'event à afficher.
     *
     * @details : Affiche les N events du cal.
     */
    public static void checkCal(int nb)throws IOException, GeneralSecurityException  {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(nb)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setQ("string")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n %s", event.getSummary(), start, event.getId());
            }
        }
    }
    /**
     * @Objet : newEvent
     * @return : un objet Event
     *
     * @details : un objet de type Event.
     */
    private static Event newEvent() {
        Event event = new Event();
        event.setSummary("New Event");
        DateTime start = new DateTime("2018-01-15T09:00:00+01:00");
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime("2018-01-15T10:00:00+01:00");
        event.setEnd(new EventDateTime().setDateTime(end));
        return event;
    }

    /**
     * @Objet : patchEvent
     * @return : un objet Event
     *
     * @param : (a ajouter : tableau des attendee)
     * @param : ( un titre si besoin de le modifier)
     * @details : modifie un event.
     */
    private static Event patchEvent(){
        Event event = new Event();
        event.setSummary("Bor booked");
        EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setOptional(true).setEmail("obadia_r@etna-alternance.net"),
                new EventAttendee().setEmail("raphael.obadia@devoteam.com")
        };
        event.setAttendees(Arrays.asList(attendees));

        return event;
    }

    /**
     * @Objet : addEvent
     *
     * @param : objet Calendar
     * @details : ajoute un evenement
     */
    private static  void addEvent(Event event) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        String calendarId = "primary";
        service.events().insert(calendarId, event).execute();
    }

    /**
     * @Objet : deleteEvent
     *
     * @param : objet Calendar
     * @param : id du calendar
     * @param : id de l'event
     *
     * @details : supprime un event
     */
    protected static void deleteEvent(String calendarId, String eventID) throws IOException, GeneralSecurityException
    {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        service.events().delete(calendarId, eventID).execute();
    }

    /**
     * @Objet : upEvent
     *
     * @param : objet Calendar
     * @param : id du calendar
     * @param : id de l'event
     * @param : un objet event
     *
     * @details : supprime un event
     */
    private static void updEvent(String calendarId, String eventId, Event event) throws IOException, GeneralSecurityException
    {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        service.events().patch(calendarId, eventId, event)
                .setSendNotifications(true)
                .execute();
        System.out.println("Event patch !");
    }
}
