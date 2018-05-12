package com.coveros.selenified.utilities;

public class Sauce {
    private Sauce() {
    }

    public static Boolean isSauce() {
        String hub = System.getProperty("hub");
        return hub != null && hub.contains("ondemand.saucelabs.com");
    }

    private static String getSauceCreds(String hub) {
        String[] parts = hub.split("@");
        if (parts.length != 2) {
            return null;
        }
        String[] startParts = parts[0].split("/");
        if (startParts.length != 3) {
            return null;
        }
        return startParts[2];
    }

    public static String getSauceUser() {
        if (!isSauce()) {
            return null;
        }
        String creds = getSauceCreds(System.getProperty("hub"));
        if (creds == null) {
            return null;
        }
        String[] parts = creds.split(":");
        if (parts.length != 2) {
            return null;
        }
        return parts[0];
    }

    public static String getSauceKey() {
        if (!isSauce()) {
            return null;
        }
        String creds = getSauceCreds(System.getProperty("hub"));
        if (creds == null) {
            return null;
        }
        String[] parts = creds.split(":");
        if (parts.length != 2) {
            return null;
        }
        return parts[1];
    }
}