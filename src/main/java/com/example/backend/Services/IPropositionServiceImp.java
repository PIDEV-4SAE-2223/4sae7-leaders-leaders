package com.example.backend.Services;
import com.example.backend.Entity.Proposition;
import com.example.backend.Repository.PropositionRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class IPropositionServiceImp extends IGenericServiceImp<Proposition,Long> implements IPropositionService{
    private final PropositionRepository propositionRepository;


}
