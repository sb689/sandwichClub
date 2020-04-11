package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {


        /* name object and children tags */
        final String SWM_NAME = "name";
        final String SWM_MAIN_NAME = "mainName";
        final String SWM_KNOWN_NAME_ARR = "alsoKnownAs";

        final String SWM_ORIGIN = "placeOfOrigin";

        final String SWM_DESC = "description";

        final String SWM_IMG = "image";

        final String SWM_INGREDIENTS_ARR = "ingredients";

        JSONObject sandwitchJsonObject = new JSONObject(json);

       /* parsing json*/
        /*parsing name object*/
        JSONObject nameObject = sandwitchJsonObject.getJSONObject(SWM_NAME);
        String mainName = nameObject.getString(SWM_MAIN_NAME);
        JSONArray knownNamesJSONArr = nameObject.getJSONArray(SWM_KNOWN_NAME_ARR);
        List<String> knownNames = new ArrayList<String>();

        for (int i=0; i< knownNamesJSONArr.length(); i++)
        {
            knownNames.add(knownNamesJSONArr.getString(i));
        }


        String origin = sandwitchJsonObject.getString(SWM_ORIGIN);
        String description = sandwitchJsonObject.getString(SWM_DESC);
        String image = sandwitchJsonObject.getString(SWM_IMG);

        /*parsing ingredients list */
        JSONArray ingredientsJSONArr = sandwitchJsonObject.getJSONArray(SWM_INGREDIENTS_ARR);
        List<String> ingredients = new ArrayList<String>();
        for (int i= 0; i< ingredientsJSONArr.length(); i++)
        {
            ingredients.add(ingredientsJSONArr.getString(i));
        }

        /*creating Sandwitch object */
        Sandwich sandwitch = new Sandwich();
        sandwitch.setMainName(mainName);
        sandwitch.setAlsoKnownAs(knownNames);
        sandwitch.setPlaceOfOrigin(origin);
        sandwitch.setDescription(description);
        sandwitch.setImage(image);
        sandwitch.setIngredients(ingredients);

        return sandwitch;
    }
}
