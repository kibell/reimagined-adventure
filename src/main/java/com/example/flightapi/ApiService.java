// This service class handles API requests to the aviationstack API.
package com.example.flightapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.json.JSONArray;

@Service
public class ApiService {

    public String callExternalApi(String departureAirportCode, String arrivalAirportCode,
                                  String flightStatus) {
        String accessKey = "07a9c7ee8fac2b94f28e8ab3c607d07c";
        String apiUrl = String.format(
                "http://api.aviationstack.com/v1/flights?access_key=%s&dep_iata=%s&arr_iata=%s&flight_status=%s",
                accessKey, departureAirportCode, arrivalAirportCode,flightStatus);

        // Make the HTTP request to the apiUrl and get the response
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        String responseBody = response.getBody();

        // Process the JSON response and format it into an HTML string
        return formatResponseAsHtml(responseBody);
    }

    // Method to format the JSON response as an HTML string
    private String formatResponseAsHtml(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray flights = jsonObject.getJSONArray("data");

        StringBuilder htmlResponse = new StringBuilder();
        // Starting the HTML document
        htmlResponse.append("<html><body>");
        htmlResponse.append("<h1>Flight Information</h1>");
        htmlResponse.append("<table border='1'>");
        htmlResponse.append("<tr><th>Flight Number</th><th>Airline</th><th>Departure</th><th>Arrival</th><th>Status</th></tr>");
        
        // Iterating over the flights array and adding each flight's details to the table
        for (int i = 0; i < flights.length(); i++) {
            JSONObject flight = flights.getJSONObject(i);
            String flightNumber = flight.getJSONObject("flight").getString("number");
            String airline = flight.getJSONObject("airline").getString("name");
            String departure = flight.getJSONObject("departure").getString("airport");
            String arrival = flight.getJSONObject("arrival").getString("airport");
            String status = flight.getString("flight_status");

            htmlResponse.append("<tr>");
            htmlResponse.append("<td>").append(flightNumber).append("</td>");
            htmlResponse.append("<td>").append(airline).append("</td>");
            htmlResponse.append("<td>").append(departure).append("</td>");
            htmlResponse.append("<td>").append(arrival).append("</td>");
            htmlResponse.append("<td>").append(status).append("</td>");
            htmlResponse.append("</tr>");
        }

        htmlResponse.append("</table>");
        htmlResponse.append("</body></html>");

        return htmlResponse.toString();
    }
}