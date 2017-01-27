package com.wirelust.stackoverflowsandbox.ID41901838;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static org.junit.Assert.fail;

public class RecurringDetailResultTest {

	private static ObjectMapper mapper = new ObjectMapper() {
		private static final long serialVersionUID = -174113593500315394L;

		{
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);
			//configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};

	@Test
	public void testParseRecurringDetailResulte() throws IOException {

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ID41901838.json");

		ObjectReader objectReader = mapper.readerFor(RecurringDetailsResult.class);
		RecurringDetailsResult result = objectReader.readValue(inputStream);

		if (result.getDetails() != null && !result.getDetails().isEmpty()) {
			RecurringDetailWrapper detail = result.getDetails().get(0);
			if (StringUtils.isEmpty(detail.getRecurringDetail().getRecurringDetailReference())) {
				fail("Recurring detail does not contain any information.");
			}
		} else {
			fail("No result details returned.");
		}

	}

}