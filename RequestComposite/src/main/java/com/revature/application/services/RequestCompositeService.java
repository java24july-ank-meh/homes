package com.revature.application.services;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.application.models.Associate;
import com.revature.application.models.Maintenance;
import com.revature.application.models.Supply;
import com.revature.application.models.Unit;

@Service
public class RequestCompositeService {

	public void sendMessage(int associateId, String message) {
		/*Associate associate = restTemplate.getForObject("http://localhost:8090/associates/" + associateId,Associate.class);*/		
		Associate associate = (Associate) getObject("associates", ""+associateId, Associate.class);
		
		// associate.getSlackId();
		message = "Your supplies have arrived!";
		// send slack direct message thing

	}

	public String getMaintenanceRequest(int id) {
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		//TODO also account for supply
		
		/*Maintenance request = restTemplate.getForObject("http://localhost:8097/maintenance/" + id, Maintenance.class);
		Unit unit = restTemplate.getForObject("http://localhost:8093/unit/" + request.getUnitId(), Unit.class);
		Associate associate = restTemplate.getForObject("http://localhost:8090/associates/"+request.getSubmittedBy(), Associate.class);*/
		Maintenance request = (Maintenance) getObject("maintenance", ""+id, Maintenance.class);
		Unit unit = (Unit) getObject("unit", ""+request.getUnitId(), Unit.class);
		Associate associate = (Associate) getObject("associates", ""+request.getSubmittedBy(), Associate.class);		

		JsonObject compositeObj = jsonParser.parse(gson.toJson(request)).getAsJsonObject();
		compositeObj.add("unit", jsonParser.parse(gson.toJson(unit)));
		compositeObj.add("associate", jsonParser.parse(gson.toJson(associate)));
		/*
		 * whole associate for the submitted by
		 * hwole unit for thee unit associated with the maintenane request
		 */
		return compositeObj.toString();
	}

	public String getSupplyRequest(int id) {
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();
		//TODO also account for supply

		/*Supply request = restTemplate.getForObject("http://localhost:8097/supply/" + id, Supply.class);
		Unit unit = restTemplate.getForObject("http://localhost:8093/unit/" + request.getUnitId(), Unit.class);
		Associate associate = restTemplate.getForObject("http://localhost:8090/associates/"+request.getSubmittedBy(), Associate.class);*/
		Supply request = (Supply) getObject("supply", ""+id, Supply.class);
		Unit unit = (Unit) getObject("unit", ""+request.getUnitId(), Unit.class);
		Associate associate = (Associate) getObject("associates", ""+request.getSubmittedBy(), Associate.class);		

		JsonObject compositeObj = jsonParser.parse(gson.toJson(request)).getAsJsonObject();
		compositeObj.add("unit", jsonParser.parse(gson.toJson(unit)));
		compositeObj.add("associate", jsonParser.parse(gson.toJson(associate)));
		/*
		 * whole associate for the submitted by
		 * hwole unit for thee unit associated with the maintenane request
		 */
		return compositeObj.toString();
	}

	public String getAllMaintenanceRequests() {

		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();

		/*Maintenance[] requests = restTemplate.getForEntity("http://localhost:8097/maintenance", Maintenance[].class).getBody();
		Unit[] units = restTemplate.getForEntity("http://localhost:8093/unit", Unit[].class).getBody();
		Associate[] associates = restTemplate.getForEntity("http://localhost:8090/associates", Associate[].class).getBody();*/
		Maintenance[] requests = (Maintenance[]) getObject("maintenance", "", Maintenance[].class);
		Unit[] units = (Unit[]) getObject("unit", "", Unit[].class);
		Associate[] associates = (Associate[]) getObject("associates", "", Associate[].class);

		JsonObject compositeObj = new JsonObject();
		compositeObj.add("requests", jsonParser.parse(gson.toJson(requests)));

		System.out.println(compositeObj.toString());
		for (JsonElement requestEntry : compositeObj.get("requests").getAsJsonArray()) {
			JsonObject compEntryJson = requestEntry.getAsJsonObject();
			for (Unit unit : units) {	
				if (compEntryJson.get("unitId").getAsInt() == unit.getUnitId()) {
					compEntryJson.add("unit", jsonParser.parse(gson.toJson(unit)));
					break;
				}
			}
			for (Associate associate : associates) {	
				if (compEntryJson.get("submittedBy").getAsInt() == associate.getAssociateId()) {
					compEntryJson.add("associate", jsonParser.parse(gson.toJson(associate)));
					break;
				}
			}
			requestEntry = compEntryJson;
			System.out.println(requestEntry.toString());
		}

		return compositeObj.toString();
	}

	public String getAllSupplyRequests() {

		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();

		/*Supply[] requests = restTemplate.getForEntity("http://localhost:8097/supply", Supply[].class).getBody();
		Unit[] units = restTemplate.getForEntity("http://localhost:8093/unit", Unit[].class).getBody();
		Associate[] associates = restTemplate.getForEntity("http://localhost:8090/associates", Associate[].class).getBody();*/
		Supply[] requests = (Supply[]) getObject("supply", "", Supply[].class);
		Unit[] units = (Unit[]) getObject("unit", "", Unit[].class);
		Associate[] associates = (Associate[]) getObject("associates", "", Associate[].class);

		JsonObject compositeObj = new JsonObject();
		compositeObj.add("requests", jsonParser.parse(gson.toJson(requests)));

		System.out.println(compositeObj.toString());
		for (JsonElement requestEntry : compositeObj.get("requests").getAsJsonArray()) {
			JsonObject compEntryJson = requestEntry.getAsJsonObject();
			for (Unit unit : units) {	
				if (compEntryJson.get("unitId").getAsInt() == unit.getUnitId()) {
					compEntryJson.add("unit", jsonParser.parse(gson.toJson(unit)));
					break;
				}
			}
			for (Associate associate : associates) {	
				if (compEntryJson.get("submittedBy").getAsInt() == associate.getAssociateId()) {
					compEntryJson.add("associate", jsonParser.parse(gson.toJson(associate)));
					break;
				}
			}
			requestEntry = compEntryJson;
			System.out.println(requestEntry.toString());
		}

		return compositeObj.toString();
	}

	private <T> Object getObject(String endpoint1, String endpoint2, Class<T> objectClass) {
		Object obj = null;
		RestTemplate restTemplate = new RestTemplate();

		if(endpoint2.isEmpty()) {
			obj = restTemplate.getForEntity(getRestServiceURI()+"/"+endpoint1, objectClass).getBody();
		}else {
			obj = restTemplate.getForEntity(getRestServiceURI()+"/"+endpoint1+"/"+endpoint2, objectClass).getBody();
		}

		return obj;
	}

	private static URI getRestServiceURI() {
		String loc = "http://localhost:8085";
		String site = "/api";//idk if this is right..?
		return UriBuilder.fromUri(loc+site).build();
	}
}
