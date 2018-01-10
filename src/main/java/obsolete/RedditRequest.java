package obsolete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class RedditRequest {

	private static final String API_URL = "https://www.reddit.com/api";
	private static final String SUBREDDIT_URL = "r/";
	private static final String TRENDING_URL = "/trending_subreddits.json";
	
	public RedditRequest() {
		super();
	}
	
	public String[] getTrending() throws IOException {

		URL requestURL = new URL(API_URL + TRENDING_URL);
		URLConnection connection = requestURL.openConnection();
		
		BufferedReader jsonIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    StringBuilder jsonFile = new StringBuilder();
    
    String jsonLine;
    while ((jsonLine = jsonIn.readLine()) != null) {
        jsonFile.append(jsonLine);
        jsonFile.append("\n");
    }
    jsonIn.close();

    JSONArray trendSR = new JSONObject(jsonFile.toString()).getJSONArray("subreddit_names");
    
    String[] toReturn = new String[trendSR.length()];
    for (int i = 0; i < trendSR.length(); i++)
    {
        toReturn[i] = (SUBREDDIT_URL + trendSR.get(i).toString());
    }
    return toReturn;
	}
}
