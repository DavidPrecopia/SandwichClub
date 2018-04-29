package com.udacity.sandwichclub.utils;

import android.text.TextUtils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
	
	private static final String LOG_TAG = JsonUtils.class.getSimpleName();
	
	private static JSONObject sandwichJson;
	
	// JSON keys
	private static final String NAMES_OBJECT = "name";
	private static final String MAIN_NAME = "mainName";
	private static final String ALSO_KNOWN_AS_ARRAY = "alsoKnowAs";
	private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
	private static final String DESCRIPTION = "description";
	private static final String IMAGE = "image";
	private static final String INGREDIENTS = "ingredients";
	
	private static final String NOT_LISTED = "Not listed";
	
	public static Sandwich parseSandwichJson(String json) {
		try {
			sandwichJson = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return getSandwichDetails();
	}
	
	private static Sandwich getSandwichDetails() {
		Sandwich sandwich = new Sandwich();
		try {
			sandwich = new Sandwich(
					mainName(),
					alsoKnownAs(),
					getString(sandwichJson, PLACE_OF_ORIGIN),
					getString(sandwichJson, DESCRIPTION),
					getString(sandwichJson, IMAGE),
					ingredients()
			);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sandwich;
	}
	
	
	private static String mainName() throws JSONException {
		if (namesObjectIsNull()) {
			return NOT_LISTED;
		}
		return getString(sandwichJson.getJSONObject(NAMES_OBJECT), MAIN_NAME);
	}
	
	private static List<String> alsoKnownAs() throws JSONException {
		if (namesObjectIsNull()) {
			return emptyListMessage();
		}
		return listFromJsonArray(
				sandwichJson.getJSONObject(NAMES_OBJECT).optJSONArray(ALSO_KNOWN_AS_ARRAY)
		);
	}
	
	private static boolean namesObjectIsNull() {
		return sandwichJson.isNull(NAMES_OBJECT);
	}
	
	
	private static List<String> ingredients() throws JSONException {
		return listFromJsonArray(sandwichJson.optJSONArray(INGREDIENTS));
	}
	
	
	private static List<String> listFromJsonArray(JSONArray jsonArray) throws JSONException {
		if (invalidJsonArray(jsonArray)) {
			return emptyListMessage();
		}
		
		List<String> list = new ArrayList<>();
		for (int x = 0; x < jsonArray.length(); x++) {
			list.add(jsonArray.getString(x));
		}
		return list;
	}
	
	private static boolean invalidJsonArray(JSONArray jsonArray) {
		return jsonArray == null || jsonArray.length() == 0;
	}
	
	private static List<String> emptyListMessage() {
		List<String> list = new ArrayList<>();
		list.add(NOT_LISTED);
		return list;
	}
	
	
	private static String getString(JSONObject jsonObject, String key) throws JSONException {
		return jsonObject.isNull(key) ? NOT_LISTED : checkIfEmpty(jsonObject.getString(key));
	}
	
	private static String checkIfEmpty(String value) {
		return TextUtils.isEmpty(value) ? NOT_LISTED : value;
	}
}