/*
KAKAO , NAVER API 통신
 */

package com.project2021.api.search;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@Service
public class SearchApiClient {

 public ResponseEntity<Object> requestSearch(String keyword){

     HashMap<String, Object> result = new HashMap<String, Object>();
     ArrayList<String> kakaoResult = new ArrayList<String>();
     ArrayList<String> naverResult = new ArrayList<String>();
     try
     {
         HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
         factory.setConnectTimeout(3000);
         factory.setReadTimeout(3000);

         ExecutorService es = Executors.newFixedThreadPool(100);
         CountDownLatch latch = new CountDownLatch(2);


       //naver
         es.submit(() -> {

             try {
                 RestTemplate restTemplate = new RestTemplate(factory);

                 HttpHeaders header = new HttpHeaders();
                 header.add("X-Naver-Client-Id", "MYEkuoyIGIuycB_sPAgz");
                 header.add("X-Naver-Client-Secret", "K83xBG74bB");
                 header.add("Content-Type", "application/json; charset=utf-8");
                 HttpEntity<?> entity = new HttpEntity<>(header);

                 String url = "https://openapi.naver.com/v1/search/local.json?query=" + keyword + "&display=10";

                 ResponseEntity<String> resultMap = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);


                 String re = resultMap.getBody();

                 JSONParser jsonParse = new JSONParser();
                 JSONObject jsonObj = (JSONObject) jsonParse.parse(re);

                 JSONArray personArray = (JSONArray) jsonObj.get("items");


                 for (int i = 0; i < personArray.size(); i++) {
                     JSONObject personObject = (JSONObject) personArray.get(i);

                     naverResult.add(personObject.get("title").toString().replaceAll("<b>", "").replaceAll("</b>", ""));
                 }

             }catch(Exception ex){
                 System.out.println(ex.toString());
             }



             latch.countDown();

             return null;
         });
         //kakao
         es.submit(() -> {
             try {
                 RestTemplate restTemplate = new RestTemplate(factory);

                 HttpHeaders header = new HttpHeaders();
                 header.add("Authorization", "KakaoAK cc69c9a17ee0418eab268858f4920b71");
                 header.add("Content-Type", "application/json; charset=utf-8");
                 HttpEntity<?> entity = new HttpEntity<>(header);

                 String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + keyword + "&size=10";

                 ResponseEntity<String> resultMap = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);


                 String re = resultMap.getBody();

                 JSONParser jsonParse = new JSONParser();
                 JSONObject jsonObj = (JSONObject) jsonParse.parse(re);

                 JSONArray personArray = (JSONArray) jsonObj.get("documents");


                 for (int i = 0; i < personArray.size(); i++) {
                     JSONObject personObject = (JSONObject) personArray.get(i);

                     kakaoResult.add(personObject.get("place_name").toString().replaceAll("<b>", "").replaceAll("</b>", ""));
                 }
             }catch(Exception ex){
                 System.out.println(ex.toString());
             }
             latch.countDown();
             return null;
         });
         es.shutdown();
         latch.await(2, TimeUnit.SECONDS);

    } catch (HttpClientErrorException | HttpServerErrorException e) {
        result.put("statusCode", e.getRawStatusCode());
        result.put("body"  , e.getStatusText());
        System.out.println(e.toString());

    } catch (Exception e) {
        System.out.println(e.toString());
    }

     JSONArray arry = new JSONArray();

     for(int i = 0; i < kakaoResult.size(); i++){
         JSONObject jsonObj = new JSONObject();

         jsonObj.put("카카오 결과" , kakaoResult.get(i));
         int idx = naverResult.indexOf(kakaoResult.get(i));
         if(idx != -1){
             System.out.println(idx);
             var r = naverResult.remove(idx);
             System.out.println(r);

         }
         arry.add(jsonObj);
     }
     for(int i = 0; i < naverResult.size(); i++){
         JSONObject jsonObj = new JSONObject();
         jsonObj.put("네이버 결과" , naverResult.get(i));
         arry.add(jsonObj);
     }





    return new ResponseEntity<>(arry, HttpStatus.OK);


 }




}
