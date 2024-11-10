package jeje.work.aeatbe.service;

import jeje.work.aeatbe.entity.BlackList;
import jeje.work.aeatbe.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final BlackListRepository blackListRepository;


    public void addBlackList (String accessToken, Long userId){
        BlackList blackList = new BlackList(accessToken, userId);
        blackListRepository.save(blackList);
    }


}
