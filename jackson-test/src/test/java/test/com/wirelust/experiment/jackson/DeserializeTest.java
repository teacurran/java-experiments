package test.com.wirelust.experiment.jackson;

import java.io.InputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wirelust.experiment.jackson.User;
import com.wirelust.experiment.jackson.UserList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Date: 21-Jul-2015
 *
 * @author T. Curran
 */
@RunWith(JUnit4.class)
public class DeserializeTest {

	@Test
	public void shouldBeAbleToDeserializeUserList() throws Exception {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream("user_list.json");

		ObjectMapper objectMap = new ObjectMapper();

		//didn't work
		objectMap.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
		//tried this too but didn't work
		objectMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		ObjectReader objectReader = objectMap.reader(UserList.class); //objectMap.readerFor(UserList.class);

		UserList userList = objectReader.readValue(is);

		Assert.assertEquals(3, userList.getUsers().size());

		User user1 = userList.getUsers().get(0);
		Assert.assertEquals("Chris Rivers", user1.getName());
	}
}
