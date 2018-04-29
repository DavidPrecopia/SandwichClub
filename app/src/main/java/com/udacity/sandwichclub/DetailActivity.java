package com.udacity.sandwichclub;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Iterator;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
	
	private Sandwich sandwich;
	private ActivityDetailBinding binding;
	
	public static final String EXTRA_POSITION = "extra_position";
	private static final int DEFAULT_POSITION = - 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
		
		sandwich = JsonUtils.parseSandwichJson(getSandwichJson());
		if (TextUtils.isEmpty(sandwich.getMainName())) {
			// Sandwich data unavailable
			closeOnError();
		}
		
		populateUI();
	}
	
	private String getSandwichJson() {
		return getResources().getStringArray(R.array.sandwich_details)[
					getPosition()
				];
	}
	
	private int getPosition() {
		int position = getIntent().getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
		if (position == DEFAULT_POSITION) {
			// EXTRA_POSITION not found in intent
			closeOnError();
		}
		return position;
	}
	
	private void closeOnError() {
		finish();
		Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
	}
	
	
	private void populateUI() {
		setTitle(sandwich.getMainName());
		binding.setSandwich(sandwich);
		loadImage();
		bindAlsoKnownAs();
		bindIngredients();
	}
	
	private void loadImage() {
		Picasso.with(this)
				.load(sandwich.getImage())
				.into(binding.imageIv);
	}
	
	private void bindAlsoKnownAs() {
		assert binding.getSandwich() != null;
		binding.alsoKnownAsTv.setText(
				stringFromList(sandwich.getAlsoKnownAs())
		);
	}
	
	private void bindIngredients() {
		assert binding.getSandwich() != null;
		binding.ingredientsTv.setText(
				stringFromList(sandwich.getIngredients())
		);
	}
	
	private String stringFromList(List<String> list) {
		StringBuilder builder = new StringBuilder();
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
			builder.append(iterator.next());
			if (iterator.hasNext()) {
				builder.append(", ");
			}
		}
		return builder.toString();
	}
}