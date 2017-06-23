package eliaskarimi;

import javax.jws.WebService;


import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
*/

import com.sun.xml.ws.developer.SchemaValidation;

import eliaskarimi.request.Request;
import eliaskarimi.response.Response;
import eliaskarimi.response.Response.Result;



//	Moeten webservice aangeven zoals besproken tijdens de les en in de sylabus
@WebService(endpointInterface="eliaskarimi.WSInterface")
@SchemaValidation // Misschien later nog iets hier mee doen? Client en service side
public class CitizensByDistrictImpl implements WSInterface{


	@Override
	public Response populationCountryByYear(Request parameters) throws ApplicationError {
		//	Creeer nieuwe response
		Response response = new Response();// factory.createResponse();
		
		//	We moeten foutmeldingen op een goede manier handelen zoals besproken in de sylabus
		//	daarom vangen we deze in een try/catch statement
		
		try {
			
			//	Kijken of het land wel valid is
			if (!countryValueIsValid(parameters.getCountry())) {
				throw new RuntimeException("Het opgegeven land bestaat niet");
			} else if (parameters.getYear() < 1950 || parameters.getYear() > 2100) {
				throw new RuntimeException("Het jaartal valt niet binnen de minimale en maximale range");
			} else {
		
			
			//	Hier plaats ik wat we echt moeten gaan doen
			//	Ik maak gebruik van de api.population.io om per land en per jaar de populatie te kunnen terugsturen
			String country = parameters.getCountry().replaceAll("\\s+", "%20");
			int year = parameters.getYear();
			
			//	Graag even kijken of we de paramters wel hebben ontvangen
			//System.out.println("country:" + country + "; year: " + year);
			

			//	Nu we de data hebben ontvangen moeten we de api aan gaan spreken
			//	De request moet in de vorm zijn van : http://api.population.io:80/1.0/population/ + year + / +country +/
			URL url = new URL("http://api.population.io:80/1.0/population/" + year + "/" + country + "/");
			
			
			HttpURLConnection req = (HttpURLConnection) url.openConnection();
            //	De api accepteert alleen get request en we willen json ontvangen
			req.setRequestMethod("GET");
			req.setRequestProperty("Accept", "application/json");
			
            //	De population api geeft een 200 code terug als de request is geslaag, anders vangen we hem op
            if (200 != req.getResponseCode()) {
            	//	TODO: Misschien later nog wat aan veranderen, geeft de api verschillende foutcodes terug?
                throw new RuntimeException("Fout opgetreden, response code was:" + req.getResponseCode());
            }
            
            //	Nu ontvangen we de data, deze lezen we met BufferedReDER EN EEN Inputsteamreader
            InputStreamReader isr = new InputStreamReader( req.getInputStream());
            BufferedReader buffer = new BufferedReader(isr);
            
            //	Nu kunnen we laten zien wat de out put wordt en tergelijkertijd de response aanvullen met wat we ontvangen ehbben
            
            String output;
            String jsonResult = "";
            
            // Output omzetten naar response, zolang we nog niet bij het einde zijn
            while (null != (output = buffer.readLine()) ) {
            	jsonResult += output;
              //  response.setResult(output);
            }
            
            // We now have the 
            //System.out.println(jsonResult);
         
            try {
            	Result res = getResultFrom(jsonResult);
            	response.setResult(res);
            } catch (ParseException e) {
            	//	Proberen fout te handelen dmv doorgven message?
            	e.printStackTrace();
            }
            //	Disconnect 
            req.disconnect();
			}
		//	Verschillende fouten kunnen ontstaan
		} catch (MalformedURLException e) {
			
			//eliaskarimi.error.ApplicationError fault = faultyFactory.createApplicationError();
			//fault.setError("Malfomered Url exception occurred");
			//fault.setMessage("Er is iets misgegaan sorry!");
			//throw fault;
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
			
		return response;
	}
	
	
	/**
	 * Een client kan een niet bestaand opgeven, dit moeten we opvangen en een goede
	 * foutmelding mee geven. Gelukkig biedt population.io een api voor de beschikbare landen.
	 * Zo kunnnen we de twee vergelijken.
	 * 
	 * @param val
	 * @return
	 * @throws ParseException
	 */
	private Boolean countryValueIsValid(String val) throws ParseException {
		try {
			URL url = new URL("http://api.population.io:80/1.0/countries");
			HttpURLConnection req = (HttpURLConnection) url.openConnection();
//			De api accepteert alleen get request en we willen json ontvangen
			req.setRequestMethod("GET");
			req.setRequestProperty("Accept", "application/json");
			
			if (200 != req.getResponseCode()) {
            	//	TODO: Misschien later nog wat aan veranderen, geeft de api verschillende foutcodes terug?
                throw new RuntimeException("Fout opgetreden, response code was:" + req.getResponseCode());
            }
			
//			Nu ontvangen we de data, deze lezen we met BufferedReDER EN EEN Inputsteamreader
            InputStreamReader isr = new InputStreamReader( req.getInputStream());
            BufferedReader buffer = new BufferedReader(isr);
            
            String output;
            String jsonResult = "";
            System.out.println("Output from Server .... \n");

            // Output omzetten naar response, zolang we nog niet bij het einde zijn
            while (null != (output = buffer.readLine()) ) {
            	jsonResult += output;
            }
            
            JSONParser parser = new JSONParser();
            JSONArray countries = (JSONArray) ((JSONObject)parser.parse(jsonResult)).get("countries");
            
            if(countries.contains(val)) {
            	 System.out.println("found!: " + val);
            	return true;
            }
             
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * Hiermee proberen we de gereturnde json string te parsen en de data te verkrijgen.
	 * 
	 * @param jsonString
	 * @return
	 * @throws ParseException
	 */
	private Result getResultFrom(String jsonString) throws ParseException {
		
		int femaleTotal = 0;
		int maleTotal = 0;
		int citizenTotal = 0;
		
        Result result = new Result();
		JSONParser parser = new JSONParser();
        JSONArray citizensByAge = (JSONArray) parser.parse(jsonString);

        //loop through each object
        for(Object o: citizensByAge){
            if ( o instanceof JSONObject ) {
            	JSONObject ageObject = (JSONObject) o;
            	//	Integers worden als long geparsed door JSONObject, moeten casten en integer krijgen
            	femaleTotal +=  ((Long)ageObject.get("females")).intValue();
            	maleTotal +=   ((Long)ageObject.get("males")).intValue();
            	citizenTotal += ((Long)ageObject.get("total")).intValue();
            }
        }
        
        citizenTotal = femaleTotal + maleTotal;
        
		result.setFemale(femaleTotal);
		result.setMale(maleTotal);
		result.setTotal(citizenTotal);

		return result;
	}

	
}
