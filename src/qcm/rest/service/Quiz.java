package qcm.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.ko.framework.KoHttp;
import net.ko.kobject.KListObject;
import qcm.adapters.DomaineAdapter;

import qcm.models.KDomaine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/quiz")
public class Quiz {
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll(){
		KListObject<KDomaine> domaines=KoHttp.getDao(KDomaine.class).readAll();
		final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(KDomaine.class, new DomaineAdapter());
		Gson gson=gsonBuilder.create();
		String result=gson.toJson(domaines.asAL());
		return result;
	}
}
