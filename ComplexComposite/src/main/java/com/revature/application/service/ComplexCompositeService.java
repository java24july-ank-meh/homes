package com.revature.application.service;

import static org.mockito.Matchers.anyByte;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.application.model.Associate;
import com.revature.application.model.Complex;
import com.revature.application.model.Notification;
import com.revature.application.model.Unit;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

@Service
public class ComplexCompositeService {

	
	public JsonArray allComplexes(){
		return getJsonArrayFromService("http://localhost:8093/complex");
	}
	
	public JsonArray allUnits() {
		JsonArray units = getJsonArrayFromService("http://localhost:8093/unit");
		JsonArray associates = getJsonArrayFromService("http://localhost:8090/");
		
		Map<Long, JsonArray> AssociatesForEachUnit = new HashMap<>();
		for(int i = 0; i < units.size(); i++) {
			JsonObject unit = units.get(i).getAsJsonObject();
			Long unitId = unit.get("unitId").getAsLong();
			AssociatesForEachUnit.put(unitId, new JsonArray());
		}
		
		for(int i = 0; i < associates.size(); i++) {
			JsonObject associate = associates.get(i).getAsJsonObject();
			if(associate.get("unitId").isJsonNull()) continue;
			Long unitId = associate.get("unitId").getAsLong();
			JsonArray unitAssociates = AssociatesForEachUnit.get(unitId);
			if(unitAssociates == null) continue;
			unitAssociates.add(associate);
		}
		
		System.out.println(AssociatesForEachUnit);
		
		for(int i = 0; i < units.size(); i++) {
			JsonObject unit = units.get(i).getAsJsonObject();
			Long unitId = unit.get("unitId").getAsLong();
			unit.add("residents", AssociatesForEachUnit.get(unitId));
		}
		
		System.out.println(units);
		
		return units;
	}
	
	public JsonObject getAllComplexes() {
		
		JsonObject compositeObj = getJsonFromService("http://localhost:8093/complex");
		JsonObject associateJson = getJsonFromService("http://localhost:8090/associates");
		JsonObject unitJson = getJsonFromService("http://localhost:8093/unit");
		RestTemplate restTemplate = new RestTemplate();

		/*Complex[] complexes = restTemplate.getForEntity("http://localhost:8093/complex", Complex[].class).getBody();
		Associate[] associates = restTemplate.getForEntity("http://localhost:8090/associates", Associate[].class).getBody();
		Unit[] units = restTemplate.getForEntity("http://localhost:8093/units", Unit[].class).getBody();*/
				
		//counting occupancy
		// get all of the units and store in a map so that way i can use ids as keys
		Map<String, Integer> unitMap = new HashMap<>();
		for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
			JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
			unitMap.put(unitEntryJson.get("unitId").getAsString(), 0);
	    }
		//then based on associates unit id add to the units to get occupancy
		//TODO: combine with bottom for loop so that residents can be added an arryay?
		for (Map.Entry<String,JsonElement> associateEntry : associateJson.entrySet()) {
			String associateUnitId = associateEntry.getValue().getAsJsonObject().get("unitId").getAsString();
		    unitMap.put(associateUnitId, unitMap.get(associateUnitId) +1);
		}
		//add an occupancy paramater for each unit
		for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
			JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
			unitEntryJson.addProperty("occupancy", unitMap.get(unitEntryJson.get("unitId").getAsString()));
			unitEntry.setValue(unitEntryJson);
		}
		
		//so now we need to add a total occupancy to the complexes
		for (Map.Entry<String,JsonElement> complexEntry : compositeObj.entrySet()) {
			JsonObject complexEntryJson = complexEntry.getValue().getAsJsonObject();
			int complexOccupancy = 0;
			int complexCapacity = 0;
			//JsonArray complexUnits = new JsonArray();
			for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
				JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
				if (complexEntryJson.get("complexId").getAsInt() == unitEntryJson.get("complexId").getAsInt()) {
					complexOccupancy += unitEntryJson.get("occupancy").getAsInt();
					complexCapacity += unitEntryJson.get("capacity").getAsInt();
					//complexUnits.add(unitJson);
				}
			}
			//each complex now has information on its own occupancy and capacity and its units
			complexEntryJson.addProperty("occupancy", complexOccupancy);
			complexEntryJson.addProperty("capacity", complexCapacity);
			//complexEntryJson.add("units", complexUnits);
			complexEntry.setValue(complexEntryJson);
		}
		
		return compositeObj;
	}
	
	public JsonObject getComplex(String id) {
		JsonObject compositeObj = getJsonFromService("http://localhost:8093/complex/"+id);
		JsonObject associateJson = getJsonFromService("http://localhost:8090/associates");
		JsonObject unitJson = getJsonFromService("http://localhost:8093/unit");
		
		//unit map so i can assign occupancies based on unitids as keys
		//exclude units that are not in this complex
		Map<String, Integer> unitMap = new HashMap<>();
		for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
			JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
			if (unitEntryJson.get("complexId").getAsInt() == compositeObj.get("complexId").getAsInt())
				unitMap.put(unitEntryJson.get("unitId").getAsString(), 0);
	    }
		
		//then based on associates unit id add to the units to get occupancy
		//TODO: combine with bottom for loop so that residents can be added an arryay?
		for (Map.Entry<String,JsonElement> associateEntry : associateJson.entrySet()) {
			String associateUnitId = associateEntry.getValue().getAsJsonObject().get("unitId").getAsString();
		    unitMap.put(associateUnitId, unitMap.get(associateUnitId) +1);
		}
		
		//give each unit an occupancy and add a unit to a json array
		JsonArray complexUnits = new JsonArray();
		for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
			JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
			if (unitEntryJson.get("complexId").getAsInt() == compositeObj.get("complexId").getAsInt()) {
				unitEntryJson.addProperty("occupancy", unitMap.get(unitEntryJson.get("unitId").getAsString()));
				complexUnits.add(unitEntryJson);
			}
			unitEntry.setValue(unitEntryJson);
	    }
		//give array of units to composite obj with the occupancies.
		//TODO make the array earlier
		compositeObj.add("units", complexUnits);
		
		return compositeObj;
	}
	
	public void unitReassignment(String jsonString) {
		JsonObject reassignJson = new JsonParser().parse(jsonString).getAsJsonObject();
		if (reassignJson.get("unitId").getAsString() == null)
			reassignJson.addProperty("unitId", "unnassigned");
		String message = "Associate /associates/" + reassignJson.get("associateId").getAsString() + 
				" would like to request a housing transfer from /units/" + reassignJson.get("unitId").getAsString() +
				" to /units/" + reassignJson.get("targetUnitId").getAsString();
		Notification notification = new Notification(message);
		
		try {
			HttpPost post = new HttpPost("http://localhost:8097/notifications/create");
			post.setHeader("Content-type", "application/json");
			
			StringEntity postingString = new StringEntity(new Gson().toJson(notification));
			post.setEntity(postingString);
			HttpClientBuilder.create().build().execute(post);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private <T> List<T> getListFromService(String url, final Class<T> clazz) {
		Client client = Client.create();
		WebResource resource = client.resource(url);
		
		ParameterizedType parameterizedGenericType = new ParameterizedType() {
	        public Type[] getActualTypeArguments() {
	            return new Type[] { clazz };
	        }

	        public Type getRawType() {
	            return List.class;
	        }

	        public Type getOwnerType() {
	            return List.class;
	        }
	    };

	    GenericType<List<T>> genericType = new GenericType<List<T>>(
	            parameterizedGenericType) {
	    };
		
		return resource.accept(MediaType.APPLICATION_JSON).get(genericType);
	}
	
	private JsonObject getJsonFromService(String url) {
		Client client = Client.create();
		WebResource resource = client.resource(url);
		String associate = resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonObject();
	}
	
	private JsonArray getJsonArrayFromService(String url) {
		Client client = Client.create();
		WebResource resource = client.resource(url);
		String associate = resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonArray();
	}

	public Object assignUnitToAssociate(int unitId, int associateId) {
		RestTemplate restTemplate = new RestTemplate();
		Associate associate = restTemplate.getForObject("http://localhost:8090/associates/"+associateId, Associate.class);
		associate.setUnitId((long) unitId);
		try {
			HttpPost post = new HttpPost("http://localhost:8090/associates/createOrUpdate");
			post.setHeader("Content-type", "application/json");
			StringEntity postingString = new StringEntity(new Gson().toJson(associate));
			post.setEntity(postingString);
			HttpClientBuilder.create().build().execute(post);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
