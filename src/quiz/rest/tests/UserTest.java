package quiz.rest.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import net.ko.framework.Ko;
import net.ko.framework.KoHttp;
import net.ko.framework.KoSession;
import net.ko.kobject.KListObject;

import org.junit.BeforeClass;
import org.junit.Test;

import qcm.models.KUtilisateur;
import qcm.utils.HttpUtils;
import qcm.utils.MyGsonBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserTest {
	private static Gson gson;
	private static String baseUrl;
	@BeforeClass
	public static void setUp(){
		gson=MyGsonBuilder.create();
		baseUrl="http://127.0.0.1:8080/Quiz-Rest/rest/";
		Ko.kstart();
	}
	
	@Test
	public void testGetAll() {
		try {
			KListObject<KUtilisateur> usersFromDb=KoSession.kloadMany(KUtilisateur.class);
			String jsonUsers=HttpUtils.getHTML(baseUrl+"user/all");
			
			Type listType = new TypeToken<List<KUtilisateur>>()
	                {
	                }.getType();
			List<KUtilisateur> users=gson.fromJson(jsonUsers, listType);
			assertEquals(users.size(), usersFromDb.count());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAddOne() {
		fail("Not yet implemented");
	}

}
