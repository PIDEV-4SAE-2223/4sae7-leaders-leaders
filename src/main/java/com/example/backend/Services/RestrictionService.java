package com.example.backend.Services;

import com.example.backend.Entity.Restriction;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestrictionService extends IGenericServiceImp<Restriction,Long> implements  IRestrictionInterface {
}
