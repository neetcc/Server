/**
 * Translate.java
 *
 * Copyright (C) 2007,  Richard Midwinter
 *
 * This file is part of google-api-translate-java.
 *
 * google-api-translate-java is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * google-api-translate-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with google-api-translate-java. If not, see <http://www.gnu.org/licenses/>.
 */
package translate.google;


import Util.HTMLEntities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;

/**
 * Makes version 2 of the Google Translate API available to Java applications.
 * 
 * @author William Ferguson
 * @author Richard Midwinter
 */
public final class TranslateV2 extends GoogleAPI implements Translate {

	/**
	 * Constants.
	 */
	private static final String URL = "https://www.googleapis.com/language/translate/v2";
	private static final String PAR_TEMPLATE = "key=%s&q=%s&target=%s";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute(final String text, final Language from, final Language to) throws GoogleAPIException {
		try {
			validateReferrer();

			if (key == null) {
				throw new IllegalStateException("You MUST have a Google API Key to use the V2 APIs. See http://code.google.com/apis/language/translate/v2/getting_started.html");
			}

			final String parameters = String.format(PAR_TEMPLATE, key, URLEncoder.encode(text, ENCODING), to.toString())
					+ (Language.AUTO_DETECT.equals(from) ? "" : String.format("&source=%s", from.toString()));

			final URL url = new URL(URL);

			final JSONObject json = retrieveJSON(url,parameters);

			return getJSONResponse(json);
		} catch (final Exception e) {
			System.out.println("Error: " +e.getMessage());

			throw new GoogleAPIException(e);
		}
	}
	
	/**
	 * Returns the JSON response data as a String. Throws an exception if the status is not a 200 OK.
	 * 
	 * @param json The JSON object to retrieve the response data from.
	 * @return The responseData from the JSONObject.
	 * @throws Exception If the responseStatus is not 200 OK.
	 */
	private static String getJSONResponse(final JSONObject json) throws Exception {
		final JSONObject data = json.getJSONObject("data");
		final JSONArray translations = data.getJSONArray("translations");
		final JSONObject translation = translations.getJSONObject(0);
		final String translatedText = translation.getString("translatedText");
		
		return HTMLEntities.unhtmlentities(translatedText);
	}
}