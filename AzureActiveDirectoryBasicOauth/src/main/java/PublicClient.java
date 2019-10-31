import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PublicClient {
    private final static String TENANTID = "**************.onmicrosoft.com";
    private final static String CLIENT_ID = "*********************************";
    private final static String CLIENT_SECRET = "***************";

    private final static String AUTHORITY = "https://login.microsoftonline.com/"+TENANTID+"/oauth2/token";
    private final static String RESOURCE = "https://graph.microsoft.com";


    public static void main(String args[]) throws Exception {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                System.in))) {
            System.out.print("Enter username: ");
            String username = br.readLine();
            System.out.print("Enter password: ");
            String password = br.readLine();

            TokenResponseDTO tokenResponseDTO = echoCuties(username, password);

            ActiveDirectoryUserDTO activeDirectoryUserDTO = getUsernamesFromGraph(tokenResponseDTO.getAccess_token());

            System.out.println(activeDirectoryUserDTO.getMail());
        }

    }

    public static TokenResponseDTO echoCuties(String username, String password) throws IOException {

        String query ="grant_type=password" +
                "&client_id=" + CLIENT_ID +
                "&resource=" + RESOURCE +
                "&client_secret=" + CLIENT_SECRET +
                "&username=" + username+
                "&password=" + password;

        String url = AUTHORITY;
        byte[] postData = query.getBytes(StandardCharsets.UTF_8);
        URL myurl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
        try {
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content.toString(), TokenResponseDTO.class);
        } finally {
            con.disconnect();
        }
    }


    private static ActiveDirectoryUserDTO getUsernamesFromGraph(String accessToken) throws Exception {
        URL url = new URL("https://graph.microsoft.com/v1.0/me");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Accept","application/json");
        int httpResponseCode = conn.getResponseCode();

        String goodRespStr = HttpClientHelper.getResponseStringFromConn(conn, true);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(goodRespStr.toString(), ActiveDirectoryUserDTO.class);
    }

}
