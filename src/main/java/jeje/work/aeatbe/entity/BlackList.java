package jeje.work.aeatbe.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("blackList")
public class BlackList {

    @Id
    private String token;

    private String id;
}
