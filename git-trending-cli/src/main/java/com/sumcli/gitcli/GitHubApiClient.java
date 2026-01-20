package com.sumcli.gitcli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumcli.gitcli.model.GitHubResponse;
import com.sumcli.gitcli.model.Repository;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

public class GitHubApiClient {

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        // Dynamic date: last 7 days (trending approximation)
        String language = "";
        String duration = "week";
        int limit = 10;

        for (int i = 0; i < args.length; i++) {
            if ("--language".equals(args[i]) && i + 1 < args.length)
                language = args[++i];
            else if ("--duration".equals(args[i]) && i + 1 < args.length) {
                duration = args[++i].toLowerCase();
                if (!List.of("day", "week", "month", "year").contains(duration)) {
                    System.err.println("Error: Invalid duration. Please use day, week, month, or year.");
                    return;
                }
            } else if ("--limit".equals(args[i]) && i + 1 < args.length)

                try {
                    limit = Integer.parseInt(args[++i]);
                } catch (NumberFormatException e) {
                    System.err.println("Error: Invalid limit. Please provide a valid integer.");
                    return;
                }
            if (limit > 100) {
                limit = 100;
            } else if (limit < 1) {
                limit = 1;
            }
        }

        long daysBack = switch (duration.toLowerCase()) {
            case "day" -> 1;
            case "month" -> 30;
            case "year" -> 365;
            default -> 7;
        };

        String date = java.time.LocalDate.now().minusDays(daysBack).toString();
        String query = "language:" + language + " created:>=" + date;
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String endpoint = "https://api.github.com/search/repositories?q=" + encodedQuery
                + "&sort=stars&order=desc&per_page=" + limit;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Accept", "application/vnd.github+json")
                .header("User-Agent", "github-trending-cli") // Required by GitHub
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                GitHubResponse gitHubResponse = objectMapper.readValue(response.body(), GitHubResponse.class);
                if (gitHubResponse.getItems() != null && !gitHubResponse.getItems().isEmpty()) {
                    for (Repository repo : gitHubResponse.getItems()) {
                        System.out.println(repo);
                    }
                } else {
                    System.out.println("No repositories found.");
                }
            } else {
                System.err.println("Error: " + response.statusCode());
                System.err.println(response.body());
            }
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}