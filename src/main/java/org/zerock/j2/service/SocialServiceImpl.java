package org.zerock.j2.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;

@Service
@Log4j2
public class SocialServiceImpl implements  SocialService{

    @Value("${org.zerock.kakao.token_url}")
    private String tokenURL;
    @Value("${org.zerock.kakao.rest_key}")
    private String clientId;
    @Value("${org.zerock.kakao.redirect_uri}")
    private String redirectURI;
    @Value("${org.zerock.kakao.get_user}")
    private String getUser;

    @Override
    public String getKakaoEmail(String authCode) {

        log.info("=================================");
        log.info("authCode: " + authCode);
        log.info(tokenURL);
        log.info(clientId);
        log.info(redirectURI);
        log.info(getUser);

        String accessToken = getAccessToken(authCode);

        String email = getEmailFromAccessToken(accessToken);

        return email;
    }
    // authCode를 통해서 accessToken을 가져오는 내부메소드
    private String getAccessToken(String authCode){

        if(authCode == null){
            throw new RuntimeException("Auth Code is null");
        }
        String accessToken = null;
        RestTemplate restTemplate = new RestTemplate();
        // hearder 값 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        // axios 로 치면 params 만드는것
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(tokenURL)
                .queryParam("grant_type","authorization_code")
                .queryParam("client_id",clientId)
                .queryParam("redirect_uri", redirectURI)
                .queryParam("code", authCode)
                .build(true);

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(
                        uriComponents.toString(), HttpMethod.POST, entity, LinkedHashMap.class);

        log.info("-----------------------------------");
        log.info(response);

        LinkedHashMap<String,String> bodayMap = response.getBody();
        accessToken = bodayMap.get("access_token");

        log.info("Access Token: " + accessToken);

        return accessToken;
    }
    // accessToken을 통해서 email을 가져오는 내부 메소드
    private String getEmailFromAccessToken(String accessToken){

        if(accessToken == null){
            throw new RuntimeException("Access Token is null");
        }
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type","application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(getUser).build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(
                        uriBuilder.toString(),
                        HttpMethod.GET,
                        entity,
                        LinkedHashMap.class);

        log.info(response);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        log.info("------------------------------------");
        log.info(bodyMap);

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");

        log.info("kakaoAccount: " + kakaoAccount);

        return kakaoAccount.get("email");

    }

}