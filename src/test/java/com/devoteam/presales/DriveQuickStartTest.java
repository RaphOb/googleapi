package com.devoteam.presales;

import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.Assert.assertNotNull;

public class DriveQuickStartTest {

    @Test
    public void createFolderTest()throws IOException, GeneralSecurityException
    {
        DriveQuickstart driveQuickstart = new DriveQuickstart();

        driveQuickstart.createFolder("new_folder", "1JPKV5A0uVnvSwLvJZtj2mEAdsUZoCaBs");
        assertNotNull(driveQuickstart.checkFile("new_folder"));

    }

    @Test
    public void getFileIdTest()throws IOException, GeneralSecurityException
    {
        DriveQuickstart driveQuickstart = new DriveQuickstart();
        assertNotNull(driveQuickstart.getFileId("new_folder"));

    }
    @Test
    public  void checkLinkTest()throws IOException, GeneralSecurityException
    {
        DriveQuickstart driveQuickstart = new DriveQuickstart();
        String res = driveQuickstart.checkLink("new_folder");
        assertNotNull(res);
    }

    @Test
    public static void copyFolder() throws IOException, GeneralSecurityException
    {
        DriveQuickstart driveQuickstart = new DriveQuickstart();
        driveQuickstart.copyFolder("1JPKV5A0uVnvSwLvJZtj2mEAdsUZoCaBs","10Jn1zUljSSeoyE0OyCJX-QfXLeMhX-hZ" );
    }

/*
    @Test
    public static void uploadFile() throws IOException, GeneralSecurityException
    {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

    }*/
}
