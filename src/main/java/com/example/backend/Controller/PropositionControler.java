package com.example.backend.Controller;

import com.example.backend.Entity.Proposition;
import com.example.backend.Services.IPropositionService;
import com.example.backend.dto.ResponseProposition;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proposition")
public class PropositionControler extends GenericController<Proposition, Long> {

    private final IPropositionService iPropositionService;

    @PostMapping("/add/question/{idQuestion}")
    @ResponseBody
    public ResponseEntity<Object> addProposition(@PathVariable Long idQuestion, @RequestBody Proposition proposition) throws Exception {

        return iPropositionService.addProposition(idQuestion,proposition);
    }

    @PostMapping("/addmultiple/question/{idQuestion}")
    @ResponseBody
    public ResponseProposition<List<Object>> addMultiProposition(@PathVariable Long idQuestion, @RequestBody List<Proposition> propositions) throws Exception {
        return iPropositionService.addMultiProposition(idQuestion,propositions);
    }
}
