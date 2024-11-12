package jeje.work.aeatbe.utility;

import jeje.work.aeatbe.dto.article.ContentDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticleUtil {
    public static String extractSubtitle(String content) {
        String[] lines = content.split("\n");
        return lines.length > 1 ? lines[1] : "";
    }

    public static List<ContentDTO> extractContentList(String content) {
        String[] lines = content.split("\n");
        List<ContentDTO> contentList = new ArrayList<>();

        if (lines.length > 0) contentList.add(new ContentDTO("h2", lines[0]));
        if (lines.length > 1) contentList.add(new ContentDTO("h3", lines[1]));
        if (lines.length > 2) contentList.add(new ContentDTO("img", lines[2]));

        if (lines.length > 3) {
            String body = String.join("\n", Arrays.copyOfRange(lines, 3, lines.length));
            contentList.add(new ContentDTO("p", body));
        }

        return contentList;
    }

    //    private Sort getSortBy(String sortby) {
    //        if ("popular".equalsIgnoreCase(sortby)) {
    //            return Sort.by(Sort.Direction.DESC, "likes");
    //        } else {
    //            return Sort.by(Sort.Direction.DESC, "date");
    //        }
    //    }

}
