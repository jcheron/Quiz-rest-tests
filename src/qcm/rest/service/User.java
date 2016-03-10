package qcm.rest.service;

import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.ko.framework.KoHttp;
import net.ko.kobject.KListObject;
import net.ko.utils.KScriptTimer;
import qcm.adapters.RangAdapter;
import qcm.adapters.UtilisateurAdapter;
import qcm.models.KRang;
import qcm.models.KUtilisateur;

@Path("/user")
public class User extends RestBase{
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll(){
		KScriptTimer.start();
		KListObject<KUtilisateur> users=KoHttp.getDao(KUtilisateur.class).readAll();
		final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(KRang.class,new RangAdapter());
	    gsonBuilder.registerTypeAdapter(KUtilisateur.class, new UtilisateurAdapter());
		Gson gson=gsonBuilder.create();
		String result=gson.toJson(users.asAL());
		KScriptTimer.stop();
		result="[{\"time\":"+KScriptTimer.get()+"},"+result+"]";
		return result;
	}
	
	@POST
	@Path("add")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public String addOne(MultivaluedMap<String, String> formParams){
		KUtilisateur user=new KUtilisateur();
		String message="{\"message\":\"Insertion réussie\"}";
		for(String param:formParams.keySet()){
			try {
				String value=formParams.get(param)+"";
				value=value.replaceFirst("^\\[(.*)\\]$", "$1");
				user.setAttribute(param, value, false);
			} catch (SecurityException | IllegalArgumentException
					| NoSuchFieldException | IllegalAccessException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
			}
		}
		try{
			KoHttp.getDao(KUtilisateur.class).create(user);
		}catch(Exception e){
			message="{\"erreur\":\""+e.getMessage()+"\"}";
		}
		return message;
	}
}
