package com.example.backend.Services;
import com.example.backend.Entity.Proposition;
import com.example.backend.Entity.QuestQuiz;
import com.example.backend.Repository.PropositionRepository;
import com.example.backend.Repository.QuestQuizRepository;
import com.example.backend.dto.ResponseProposition;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class IPropositionServiceImp extends IGenericServiceImp<Proposition,Long> implements IPropositionService{
    private final PropositionRepository propositionRepository;
    private final QuestQuizRepository questQuizRepository;




    @Override
    public ResponseEntity<Object> addProposition(Long idQuestion,Proposition proposition) throws Exception {
        QuestQuiz questionsQuizz = questQuizRepository.findByIdQuestQuizz(idQuestion);
        if (questionsQuizz == null  ) {
            throw new Exception("Id quizz is required!");
        }

        if (questionsQuizz.getPropositions().size()<3) {
            try {
                proposition.setQuest_quiz(questionsQuizz);
                Proposition propositionRepo = propositionRepository.save(proposition);
                return ResponseEntity.ok(propositionRepo);
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }
        return ResponseEntity.ok("You already added 3 propositions for this question");
    }

    @Override
    public ResponseProposition<List<Object>> addMultiProposition(Long idQuestion, List<Proposition> propositions) throws Exception {
        QuestQuiz questionsQuizz = questQuizRepository.findByIdQuestQuizz(idQuestion);
        if (questionsQuizz == null) {
            throw new Exception("Id quizz is required!");
        }
        try {
            List<Proposition> propositionsList = new ArrayList<>();
            for (Proposition proposition : propositions) {
                proposition.setQuest_quiz(questionsQuizz);
                Proposition propositionAdd = propositionRepository.save(proposition);
                propositionsList.add(propositionAdd);
            }
            ResponseProposition response = new ResponseProposition(propositionsList);
            return response;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

}
