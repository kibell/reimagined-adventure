package com.example.flightapi.service;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class XMLUserService {

    private static final String XML_FILE = "users.xml";

    public XMLUserService() {
        ensureXMLFileExists();
    }

    private void ensureXMLFileExists() {
        try {
            File file = new File(XML_FILE);
            if (!file.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("users");
                doc.appendChild(rootElement);
                saveXML(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Document loadXML() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            return dBuilder.parse(new File(XML_FILE));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveXML(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XML_FILE));
            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public boolean register(String username, String password) {
        try {
            Document doc = loadXML();
            NodeList users = doc.getElementsByTagName("user");

            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                if (user.getElementsByTagName("username").item(0).getTextContent().equals(username)) {
                    System.out.println("User already exists");
                    return false;
                }
            }

            Element newUser = doc.createElement("user");
            Element newUsername = doc.createElement("username");
            newUsername.appendChild(doc.createTextNode(username));
            Element newPassword = doc.createElement("password");
            newPassword.appendChild(doc.createTextNode(password));
            newUser.appendChild(newUsername);
            newUser.appendChild(newPassword);
            doc.getDocumentElement().appendChild(newUser);
            saveXML(doc);
            System.out.println("User registered successfully");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticate(String username, String password) {
        try {
            Document doc = loadXML();
            if (doc == null) {
                System.out.println("Failed to load XML document");
                return false;
            }

            NodeList users = doc.getElementsByTagName("user");
            System.out.println("Number of users found: " + users.getLength());

            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                String storedUsername = user.getElementsByTagName("username").item(0).getTextContent();
                String storedPassword = user.getElementsByTagName("password").item(0).getTextContent();
                System.out.println("Checking user: " + storedUsername);

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    System.out.println("Login successful");
                    return true;
                }
            }

            System.out.println("Invalid username or password");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}