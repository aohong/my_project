package test;

import com.bjcre.server.WebStart;

public class TestWarWebStart {
    public static void main(String[] args) throws Exception {
        WebStart.start(18080, "/test", "/Users/aohong/Documents/workspace/settle/my-test-project/target/web-server-1.0-SNAPSHOT.war");
    }
}
