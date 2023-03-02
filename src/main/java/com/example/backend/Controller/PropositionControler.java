package com.example.backend.Controller;

import com.example.backend.Entity.Proposition;
import com.example.backend.Services.IPropositionService;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proposition")
public class PropositionControler extends GenericController<Proposition,Long> {

    private final IPropositionService iService;


}
