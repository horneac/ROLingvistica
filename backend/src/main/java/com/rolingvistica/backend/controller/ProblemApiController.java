package com.rolingvistica.backend.controller;

import com.rolingvistica.backend.service.ProblemService;
import io.rolingvistica.api.ProblemApi;
import io.rolingvistica.dto.ContestProblemsDTO;
import io.rolingvistica.dto.ProblemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProblemApiController implements ProblemApi {
    private final ProblemService problemService;
    /**
     * GET /problem/old : get all problems
     *
     * @return A list containing all problems (status code 200)
     */
    @Override
    public ResponseEntity<List<ProblemDTO>> getAll() {
        return new ResponseEntity<>(problemService.getAll(), HttpStatus.OK);
    }

    /**
     * GET /problem : returns all the problems grouped by section and contests
     * returns all the problems grouped by section and contests
     *
     * @return A JSON (status code 200)
     */
    @Override
    public ResponseEntity<List<ContestProblemsDTO>> listProblemsGroupedBySectionAndContest() {
        return new ResponseEntity<>(problemService.listProblemsGroupedBySectionAndContest(), HttpStatus.OK);
    }
}
