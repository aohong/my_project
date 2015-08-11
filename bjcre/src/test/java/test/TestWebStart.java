package test;

import com.bjcre.server.WebStart;

public class TestWebStart {
	public static void main(String[] args) throws Exception {
		WebStart.start(8080, "/test", "src/main/webapp");
	}
}
