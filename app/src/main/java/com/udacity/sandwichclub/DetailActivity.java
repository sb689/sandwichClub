package com.udacity.sandwichclub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG= DetailActivity.class.getSimpleName();
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mOriginTV;
    private TextView mDescriptionTV;
    private TextView mIngredientsTV;
    private TextView mAlsoKnownTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOriginTV = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTV = (TextView) findViewById(R.id.description_tv);
        mIngredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        mAlsoKnownTV = (TextView) findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        mOriginTV.setText(sandwich.getPlaceOfOrigin());

        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        String alsoKnownText = "";
        for(String known: alsoKnownAs) {
            alsoKnownText += known + ", ";

        }
        alsoKnownText = alsoKnownText.replaceAll(", $", "");
        mAlsoKnownTV.setText(alsoKnownText);

        mDescriptionTV.setText(sandwich.getDescription());

        List<String> ingredients = sandwich.getIngredients();
        String ingredientText = "";
        for(String  ingredient: ingredients) {
            ingredientText += ingredient + ", ";

        }
        ingredientText = ingredientText.replaceAll(", $","");
        mIngredientsTV.setText(ingredientText);


        Log.v(TAG, "origin := " + sandwich.getPlaceOfOrigin());
        Log.v(TAG, "alsoKnown = " + sandwich.getAlsoKnownAs());
        Log.v(TAG, "description = " + sandwich.getDescription());
        Log.v(TAG, "ingredients = " + sandwich.getIngredients().toString());
        Log.v(TAG, "ingredients length = " + ingredients.size());
    }
}
