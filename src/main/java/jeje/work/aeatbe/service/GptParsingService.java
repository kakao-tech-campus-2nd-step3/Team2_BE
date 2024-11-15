package jeje.work.aeatbe.service;

import java.util.ArrayList;
import java.util.List;
import jeje.work.aeatbe.dto.user.UserInfoUpdateReqeustDTO;
import jeje.work.aeatbe.entity.User;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptParsingService {
    private final UserService userService;

    public void jsonParsing(String jsonResponse, Long userId) {
        UserInfoUpdateReqeustDTO userInfoUpdateReqeustDto = parseJsonToGpt(jsonResponse, userId);

        userService.updateUserInfo(userInfoUpdateReqeustDto, userId);
    }

    private UserInfoUpdateReqeustDTO parseJsonToGpt(String response, Long userId) {
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
            JSONArray choicesArray = (JSONArray) jsonObject.get("choices");
            JSONObject choiceObject = (JSONObject) choicesArray.get(0);
            JSONObject messageObject = (JSONObject) choiceObject.get("message");

            String content = (String) messageObject.get("content");
            List<String> userAllergies = extractAllergyNames(content);

            return parseJsonToUserDTO(userAllergies, userId);

        } catch (ParseException e) {
            throw new RuntimeException("JSON 파싱 중 에러 발생: ", e);
        }
    }

    private UserInfoUpdateReqeustDTO parseJsonToUserDTO(List<String> userAllergies, Long userId) {
        User user = userService.findById(userId);
        return UserInfoUpdateReqeustDTO.builder()
            .userName(user.getUserName())
            .allergies(userAllergies)
            .build();
    }

    private List<String> extractAllergyNames(String content) {
        List<String> allergies = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(content);

            for (Object obj : jsonArray) {
                JSONObject allergyObj = (JSONObject) obj;
                String allergyName = (String) allergyObj.values().iterator().next();
                allergies.add(allergyName);
            }

        } catch (ParseException e) {
            throw new RuntimeException("알레르기 정보 파싱 중 에러 발생: ", e);
        }

        return allergies;
    }
}