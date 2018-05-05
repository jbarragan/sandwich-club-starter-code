package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject sandwichJson = new JSONObject(json);

            JSONObject name = sandwichJson.getJSONObject("name");
            String mainName = name.getString("mainName");

            ArrayList<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            if( alsoKnownAsArray != null ) {
                int length = alsoKnownAsArray.length();
                for( int i = 0; i < length ; i++){
                    alsoKnownAs.add(alsoKnownAsArray.get(i).toString());
                }
            }

            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");

            String description = sandwichJson.getString("description");

            String image = sandwichJson.getString("image");

            ArrayList<String> ingredients = new ArrayList<>();
            JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
            if( ingredientsArray != null ) {
                int length = ingredientsArray.length();
                for (int i = 0; i < length; i++){
                    ingredients.add(ingredientsArray.get(i).toString());
                }
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return sandwich;
    }
}
