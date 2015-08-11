package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * 一般性测试代码，非测试用例
 */
@RunWith(Parameterized.class)
public class QueryTest {

    @Parameters
    public static Collection<String[]> prepareData() throws IOException {
        List<String[]> urlList = new ArrayList<String[]>();
        File in = new File("src/test/resources/data/url.txt");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(in));

        String text = "";
        while ((text = reader.readLine()) != null) {
            String[] objects = text.split(",");
            urlList.add(objects);
        }

        return urlList;
    }

    String url;

    public QueryTest(String url) {
        this.url = url.trim();
    }

    @Test(expected = IOException.class)
    public void test() throws IOException {
//        String url = SettleTestBase.SETTLE_URL + this.url;
        System.out.println(url);

        String resultJson = null;
//        resultJson = RestHttpUtils.doGet(url).getBody();
//
//        System.out.println(resultJson);
    }
}
