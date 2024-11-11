package jeje.work.aeatbe.service;

import java.util.Optional;
import jeje.work.aeatbe.entity.BlackList;
import jeje.work.aeatbe.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final BlackListRepository blackListRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 로그아웃된 엑세스 토큰을 블랙 리스트에 등록
     * @param accessToken
     * @param userId
     */
    public void addBlackList (String accessToken, String userId){
        accessToken = removePrefix(accessToken);
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

    /**
     * 블랙시스트에 있는지 확인
     * @param accessToken
     * @return 유무
     */
    @Transactional(readOnly = true)
    public boolean isInBlackList(String accessToken){
        Optional<BlackList> blackList = blackListRepository.findById(accessToken);

        return !blackList.isEmpty();
    }


}
