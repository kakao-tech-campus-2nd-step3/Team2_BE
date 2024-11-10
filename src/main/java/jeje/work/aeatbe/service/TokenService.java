package jeje.work.aeatbe.service;

import jeje.work.aeatbe.entity.BlackList;
import jeje.work.aeatbe.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final BlackListRepository blackListRepository;

    /**
     * 로그아웃된 엑세스 토큰을 블랙 리스트에 등록
     * @param accessToken
     * @param userId
     */
    public void addBlackList (String accessToken, Long userId){
        BlackList blackList = new BlackList(accessToken, userId);
        blackListRepository.save(blackList);
    }

    /**
     * 토큰의 Bearer prefix 제거
     * @param token
     * @return prefix없는 순수 토큰
     */
    public String removePrefix(String token){
        if(token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return token;
    }


}
