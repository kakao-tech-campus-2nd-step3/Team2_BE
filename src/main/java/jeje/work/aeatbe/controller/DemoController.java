package jeje.work.aeatbe.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class DemoController {

    /**
     * 루트 경로에 생성할 페이지
     *
     * @return welcome text
     */
    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String demo() {
        String asciiArt =
                " '|| '||'  '|' '||''''|  '||'        ..|'''.|  ..|''||   '||    ||' '||''''|  \n" +
                "  '|. '|.  .'   ||  .     ||       .|'     '  .|'    ||   |||  |||   ||  .    \n" +
                "   ||  ||  |    ||''|     ||       ||         ||      ||  |'|..'||   ||''|    \n" +
                "    ||| |||     ||        ||       '|.      . '|.     ||  | '|' ||   ||       \n" +
                "     |   |     .||.....| .||.....|  ''|....'   ''|...|'  .|. | .||. .||.....| \n" +
                "                                                                               \n" +
                "    ___    ______      __ \n" +
                "   /   |  / ____/___ _/ /_\n" +
                "  / /| | / __/ / __ `/ __/\n" +
                " / ___ |/ /___/ /_/ / /_  \n" +
                "/_/  |_/_____/(__,_/(__/  \n" +
                "                                                             \n" +
                "██████╗  █████╗  ██████╗██╗  ██╗███████╗███╗   ██╗██████╗ \n" +
                "██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝████╗  ██║██╔══██╗\n" +
                "██████╔╝███████║██║     █████╔╝ █████╗  ██╔██╗ ██║██║  ██║\n" +
                "██╔══██╗██╔══██║██║     ██╔═██╗ ██╔══╝  ██║╚██╗██║██║  ██║\n" +
                "██████╔╝██║  ██║╚██████╗██║  ██╗███████╗██║ ╚████║██████╔╝\n" +
                "╚═════╝ ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═════╝ \n" +
                "                                                          \n" +
                "███████╗███████╗██████╗ ██╗   ██╗██╗ ██████╗███████╗██╗   \n" +
                "██╔════╝██╔════╝██╔══██╗██║   ██║██║██╔════╝██╔════╝██║   \n" +
                "███████╗█████╗  ██████╔╝██║   ██║██║██║     █████╗  ██║   \n" +
                "╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██║     ██╔══╝  ╚═╝   \n" +
                "███████║███████╗██║  ██║ ╚████╔╝ ██║╚██████╗███████╗██╗   \n" +
                "╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝ ╚═════╝╚══════╝╚═╝   \n" +
                "                                                          \n";

        return asciiArt;
    }
}
