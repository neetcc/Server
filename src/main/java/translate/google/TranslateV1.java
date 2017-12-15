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
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;

/**
 * Makes the Google Translate API available to Java applications.
 * 
 * @author Richard Midwinter
 * @author Mike Nereson
 * @author Emeric Vernat
 * @author Juan B Cabral
 * @author Kramar Tomas
 */
@Deprecated
public final class TranslateV1 extends GoogleAPI implements Translate {
	
	/**
	 * Constants.
	 */
    private static final String
    		LANG_PARAM = "&langpair=",
    		TEXT_PARAM = "&q=",
    		PIPE_PARAM = "%7C",
    		URL = "http://ajax.googleapis.com/ajax/services/language/translate",
    		PARAMETERS = "v=2.0&langpair=#FROM#%7C#TO#&q=";

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(final String text, final Language from, final Language to) throws GoogleAPIException {
    	try {
	    	validateReferrer();
	    	
	    	final URL url = new URL(URL);
	    	final String parameters = PARAMETERS.replaceAll("#FROM#", from.toString()).replaceAll("#TO#", to.toString())
	    			+URLEncoder.encode(text, ENCODING) +(key != null ? "&key=" +key : "");
	    	
	    	final JSONObject json = retrieveJSON(url, parameters);
	    	
	    	return getJSONResponse(json);
    	} catch (final Exception e) {
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
    	if (json.getString("responseStatus").equals("200")) {
    		final String translatedText = json.getJSONObject("responseData").getString("translatedText");
    		return HTMLEntities.unhtmlentities(translatedText);
    	} else {
    		throw new GoogleAPIException("Google returned the following error: [" +json.getString("responseStatus") +"] "
    				+json.getString("responseDetails"));
    	}
    }
}