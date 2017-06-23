package eliaskarimi;

import javax.jws.WebService;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
/**
*/

import com.sun.xml.ws.developer.SchemaValidation;

import eliaskarimi.request.Request;
import eliaskarimi.response.Response;

//	Moeten webservice aangeven zoals besproken tijdens de les en in de sylabus
@WebService(endpointInterface="eliaskarimi.WSInterface")
@SchemaValidation // Misschien later nog iets hier mee doen? Client en service side
public class CitizensByDistrictImpl implements WSInterface{


	@Override
	public Response populationCountryByYear(Request parameters) throws ApplicationError {
		// TODO Auto-generated method stub
		//	Creeer nieuwe response
		Response response = new Response();
		
		//	We moeten foutmeldingen op een goede manier handelen zoals besproken in de sylabus
		//	daarom vangen we deze in een try/catch statement
		
		try {
			//	Hier plaats ik wat we echt moeten gaan doen
			//	Ik maak gebruik van de api.population.io om per land en per jaar de populatie te kunnen terugsturen
			String country = parameters.getCountry().replaceAll("\\s+", "+");
			int year = parameters.getYear();
			
			//	Graag even kijken of we de paramters wel hebben ontvangen
			System.out.println("country:" + country + "; year: " + year);
			
			
			
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
            System.out.println("Output from Server .... \n");

            // Output omzetten naar response, zolang we nog niet bij het einde zijn
            while (null != (output = buffer.readLine()) ) {
                response.setResult(output);
                System.out.println(output);
            }

            req.disconnect();
            
		//	Verschillende fouten kunnen ontstaan
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return response;
	}

	
}
