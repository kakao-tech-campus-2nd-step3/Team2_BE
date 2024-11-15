package jeje.work.aeatbe.utility;

import org.springframework.stereotype.Component;

/**
 * ArticleUtil
 */
@Component
public class ArticleUtil {
    /**
     * 글의 내용에서 첫 번째 줄을 추출합니다.
     * @param content
     * @return
     */
    public String getFirstLine(String content) {
        String[] lines = content.split("\n");
        return lines.length > 1 ? lines[1] : "";
    }
}
