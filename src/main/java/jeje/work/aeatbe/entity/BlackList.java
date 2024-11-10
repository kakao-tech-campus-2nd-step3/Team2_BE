package jeje.work.aeatbe.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "blackList" , timeToLive = 3600)
public class BlackList {

    @Id
    private String token;

    private Long id;
}
