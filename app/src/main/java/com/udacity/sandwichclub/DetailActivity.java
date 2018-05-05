package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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
        TextView origin_tv = findViewById(R.id.origin_tv);
        origin_tv.setText(sandwich.getPlaceOfOrigin());

        TextView description_tv = findViewById(R.id.description_tv);
        description_tv.append(sandwich.getDescription());

        TextView also_known_tv = findViewById(R.id.also_known_tv);
        StringBuilder also_know_as_text = new StringBuilder();
        for( String also_know_as : sandwich.getAlsoKnownAs() )
            also_know_as_text.append("\t\t").append(also_know_as).append("\n");
        also_known_tv.setText(also_know_as_text.toString());

        TextView ingredients_tv = findViewById(R.id.ingredients_tv);
        StringBuilder ingredient_text = new StringBuilder();
        for( String ingredient : sandwich.getIngredients() )
            ingredient_text.append("\t\t").append(ingredient).append("\n");
        ingredients_tv.setText(ingredient_text.toString());
    }
}
