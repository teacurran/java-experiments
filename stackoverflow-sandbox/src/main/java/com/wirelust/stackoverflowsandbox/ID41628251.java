package com.wirelust.stackoverflowsandbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


/**
 * Date: 12-Jan-2017
 *
 * @author T. Curran
 */
public class ID41628251 {

	private static final Logger LOGGER = LoggerFactory.getLogger(ID41628251.class);

	private String getContentDescription(MultipartFile file, Long contentCategoryId) {
		try (InputStream inputStream = file.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
			StringBuilder sb = new StringBuilder();

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
			return sb.toString();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
}
