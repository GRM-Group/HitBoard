/**
 * 
 */
package pl.grmdev.hitboard.requests.web;

import java.util.*;
import java.util.concurrent.Future;

import org.json.*;

import com.mashape.unirest.http.*;

import pl.grmdev.hitboard.requests.RequestHandler;
import pl.grmdev.hitboard.requests.util.HbGet;
/**
 * @author Levvy055
 */
public class Games {
	
	/**
	 * Gets list of games available on hitbox.tv
	 * 
	 * @return {@link List}
	 * @throws Exception
	 */
	public List<String> getGamesList() throws Exception {
		Future<HttpResponse<JsonNode>> gamesAsyncTask = RequestHandler
				.instance().get(HbGet.GAMES_LIST).queryString("limit", 29000)
				.asJsonAsync();
		ArrayList<String> games = new ArrayList<>();
		HttpResponse<JsonNode> httpResponse = gamesAsyncTask.get();
		JSONObject gamesJson = httpResponse.getBody().getObject();
		JSONArray gamesArray = gamesJson.getJSONArray("categories");
		for (int i = 0; i < gamesArray.length(); i++) {
			if (gamesArray.get(i) == null) {
				continue;
			}
			JSONObject catElem = gamesArray.getJSONObject(i);
			if (catElem.isNull("category_name")) {
				continue;
			}
			String catName = catElem.getString("category_name");
			if (catName == null || catName == "") {
				continue;
			}
			games.add(catName);
		}
		return games;
	}
}
