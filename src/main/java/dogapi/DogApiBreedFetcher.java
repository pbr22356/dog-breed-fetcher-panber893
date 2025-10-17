package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed)
            throws BreedFetcher.BreedNotFoundException {
        try {
            if (breed == null || breed.isBlank()) {
                throw new BreedNotFoundException("Breed must not be null or blank", null);
            }

            String norm = breed.toLowerCase().trim();
            String url = "https://dog.ceo/api/breed/" + norm + "/list";

            Request request = new Request.Builder().url(url).get().build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    throw new BreedNotFoundException(
                            "HTTP error " + response.code() + " when calling " + url, null);
                }

                String body = response.body().string();
                JSONObject json = new JSONObject(body);

                String status = json.optString("status", "");
                if ("error".equalsIgnoreCase(status)) {
                    String msg = json.optString("message", "Breed not found");
                    int code = json.optInt("code", -1);
                    if (code == 404 || msg.toLowerCase().contains("breed not found")) {
                        throw new BreedNotFoundException(msg, null);
                    }
                    throw new BreedNotFoundException("API error: " + msg, null);
                }

                if (!"success".equalsIgnoreCase(status)) {
                    throw new BreedNotFoundException("Unexpected API status: " + status, null);
                }

                JSONArray arr = json.getJSONArray("message");
                List<String> subs = new ArrayList<>(arr.length());
                for (int i = 0; i < arr.length(); i++) {
                    subs.add(arr.getString(i).toLowerCase());
                }
                return subs;
            }
        } catch (BreedNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BreedFetcher.BreedNotFoundException("Failed to fetch sub-breeds", e);
        }
    }
}