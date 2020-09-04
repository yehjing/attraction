
import com.alibaba.fastjson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class FileDownload {

    public List<AttractionVo> getArrList(String url) {

//	 	String url = "https://gis.taiwan.net.tw/XMLReleaseALL_public/scenic_spot_C_f.json";

        String json = loadJson(url);
        int index = json.indexOf("{");
        json = json.substring(index);
//        json = json.substring(json.indexOf("["), json.lastIndexOf("]"));

        JSONObject jsonObj = JSON.parseObject(json);

//        JSONArray jsonArr = jsonObj.getJSONObject("XML_Head").getJSONObject("Infos").getJSONArray("Info");
        String jsonArr = JSON.toJSONString(jsonObj.getJSONObject("XML_Head").getJSONObject("Infos").getJSONArray("Info"));

        List<AttractionVo> list = JSON.parseArray(jsonArr, AttractionVo.class);

//        List<AttractionVo> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            String jsonString = JSON.toJSONString(jsonArr.get(i));
//            AttractionVo vo = JSON.parseObject(jsonString, AttractionVo.class);
//            list.add(vo);
//        }

        return list.subList(0, 5);

    }

    private String loadJson(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }

}

