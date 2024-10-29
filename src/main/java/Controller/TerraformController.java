package Controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/terraform")
public class TerraformController {

    /**
     * 전달받은 명령어를 실행합니다.
     *
     * @param request 명령어 요청 객체
     * @return 실행 결과
     */
    @PostMapping("/execute")
    public ResponseEntity<String> executeCommand(@RequestBody CommandRequest request) {
        String commands = request.getCommands();

        try {
            // 명령어 실행
            Process process = Runtime.getRuntime().exec(new String[] {"sh", "-c", commands});

            // 실행 결과를 읽어오기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return ResponseEntity.status(500).body("명령어 실행 실패, 종료 코드: " + exitCode + "\n" + output);
            }

            return ResponseEntity.ok("명령어 실행 성공\n" + output);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("명령어 실행 중 오류 발생: " + e.getMessage());
        }
    }
}

@Setter
@Getter
class CommandRequest {
    private String commands;

}