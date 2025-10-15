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
    public List<String> getSubBreeds(String breed) {
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        // return statement included so that the starter code can compile and run.
        
        Response response = null;
        try {
            response = client.newCall(new Request.Builder()
                .url("https://dog.ceo/api/breed/" + breed + "/list")
                .build()).execute();
        } catch (IOException e) {
            throw new BreedNotFoundException(breed);
        }

        if (response.isSuccessful()) {
            String jsonData = null;
            try {
                jsonData = response.body().string();
            } catch (IOException e) {
                throw new BreedNotFoundException(breed);
            }

            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getString("status").equals("success")) {
                JSONArray subBreeds = jsonObject.getJSONArray("message");
                List<String> result = new ArrayList<>();
                for (int i = 0; i < subBreeds.length(); i++) {
                    result.add(subBreeds.getString(i));
                }
                return result;
            } else {
                throw new BreedNotFoundException(breed);
            }
        } else {
            throw new BreedNotFoundException(breed);
        }

        // return new ArrayList<>();
    }
}