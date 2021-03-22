package com.project2021.api.search;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;


import lombok.var;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@Service
public class SearchApiClient {

public ResponseEntity<Object> requestSearch(String keyword){
    JSONArray arry = new JSONArray();
    ArrayList<String> kakaoResult = new ArrayList<String>();
    ArrayList<String> naverResult = new ArrayList<String>();
    ArrayList<String> result = new ArrayList<String>();

try {
    //객체를 통해 타임아웃 제어
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(3000); //타임아웃 설정 3초
    factory.setReadTimeout(3000); // 타임아웃 설정 3초

    //ThreadPool 사용
    //thread 2개 생성
    ExecutorService es = Executors.newFixedThreadPool(2);
    CountDownLatch latch = new CountDownLatch(2);


    //네이버
    es.submit(() -> {
        try {
            //resTemplate 객체로 api 호출
            RestTemplate restTemplate = new RestTemplate(factory);

            //header 설정
            HttpHeaders header = new HttpHeaders();
            header.add("X-Naver-Client-Id", "MYEkuoyIGIuycB_sPAgz");
            header.add("X-Naver-Client-Secret", "K83xBG74bB");
            header.add("Content-Type", "application/json; charset=utf-8");
            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = "https://openapi.naver.com/v1/search/local.json?query=" + keyword + "&display=10";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
            ResponseEntity<String> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, String.class);

            String re = resultMap.getBody();

            JSONParser jsonParse = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParse.parse(re);

            JSONArray personArray = (JSONArray) jsonObj.get("items");
            JSONObject jo = new JSONObject();

            for (int i = 0; i < personArray.size(); i++) {
                JSONObject personObject = (JSONObject) personArray.get(i);

                naverResult.add(personObject.get("title").toString().replaceAll("<b>", "").replaceAll("</b>", ""));
            }
            jo.put("naver" , naverResult);
            arry.add(jo);
        }catch(Exception e){
            JSONObject jo = new JSONObject();
            jo.put("statusCode","999");
            jo.put("body","Naver Exception Error");
            arry.add(jo);
            System.out.println(e.toString());
        }


    });

    //카카오
    es.submit(() -> {
        try {
            //resTemplate 객체로 api 호출
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            header.add("Authorization", "KakaoAK cc69c9a17ee0418eab268858f4920b71");
            header.add("Content-Type", "application/json; charset=utf-8");
            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + keyword + "&size=10";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
            ResponseEntity<String> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, String.class);

            String re = resultMap.getBody();


            JSONParser jsonParse = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParse.parse(re);

            JSONArray personArray = (JSONArray) jsonObj.get("documents");


            for (int i = 0; i < personArray.size(); i++) {
                JSONObject personObject = (JSONObject) personArray.get(i);

                kakaoResult.add(personObject.get("place_name").toString().replaceAll("<b>", "").replaceAll("</b>", ""));
            }
            JSONObject jo = new JSONObject();
            jo.put("kakao" , kakaoResult);
            arry.add(jo);
        }catch(Exception e){
            JSONObject jo = new JSONObject();
            jo.put("statusCode","999");
            jo.put("body","KAKAO Exception Error");
            System.out.println(e.toString());
            arry.add(jo);
        }
    });

    es.shutdown();
    latch.await(2, TimeUnit.SECONDS);


    for(int i = 0; i < kakaoResult.size(); i++){

        //중복시 카카오 기준
        int idx = naverResult.indexOf(kakaoResult.get(i));
        if(idx != -1){
            System.out.println(idx);
            var r = naverResult.remove(idx);
            System.out.println(r);
        }
        result.add(kakaoResult.get(i));
    }
    for(int i = 0; i < naverResult.size(); i++){
        result.add(naverResult.get(i));
    }
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("결과" , result);
    arry.add(jsonObj);



    }catch(HttpClientErrorException | HttpServerErrorException e){
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("statusCode",e.getRawStatusCode());
    jsonObj.put("body",e.getStatusText());
     System.out.println(e.toString());
    arry.add(jsonObj);
    }catch(Exception e){
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("statusCode","999");
    jsonObj.put("body","Exception Error");
    arry.add(jsonObj);
    System.out.println(e.toString());
    }

    return new ResponseEntity<>(arry, HttpStatus.OK);
}


}
