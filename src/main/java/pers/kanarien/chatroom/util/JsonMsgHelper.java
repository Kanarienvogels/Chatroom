package pers.kanarien.chatroom.util;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMsgHelper {

	private static final ObjectMapper JSONMAPPER = new ObjectMapper();

	public static void writeJson(HttpServletResponse response, Object pojo, HttpStatus status)
			throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(status.value());
		writeJson(response.getWriter(), pojo);
	}

	public static void writeJson(Writer writer, Object pojo)
			throws IOException {
		JSONMAPPER.writeValue(writer, pojo);
	}
}
