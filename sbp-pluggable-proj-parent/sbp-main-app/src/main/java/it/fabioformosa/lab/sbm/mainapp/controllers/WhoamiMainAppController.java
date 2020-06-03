package it.fabioformosa.lab.sbm.mainapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main-app")
public class WhoamiMainAppController {

  //  @Autowired
  //  NamedParameterJdbcTemplate jdbcTemplate;

  @GetMapping("/whoami")
  public String hello() {
    return "Hello World, I'm the main app!";
  }

  //    @Data
  //    static class Result {
  //        private final int left;
  //        private final int right;
  //        private final long answer;
  //    }
  //
  //    // SQL sample
  //    @RequestMapping("calc")
  //    Result calc(@RequestParam int left, @RequestParam int right) {
  //        MapSqlParameterSource source = new MapSqlParameterSource()
  //                .addValue("left", left)
  //                .addValue("right", right);
  //        return jdbcTemplate.queryForObject("SELECT :left + :right AS answer", source,
  //                (rs, rowNum) -> new Result(left, right, rs.getLong("answer")));
  //    }
}
