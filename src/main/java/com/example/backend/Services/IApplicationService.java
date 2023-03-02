package com.example.backend.Services;

import com.example.backend.Entity.Equipment;
import com.example.backend.Entity.SupplierApplication;
import com.example.backend.generic.IGenericService;

public interface IApplicationService extends IGenericService<SupplierApplication,Long> {

    SupplierApplication affecterSupplierApplication(Long ids,Long ida);
    SupplierApplication affecterOfferApplication(Long ido,Long ida);
}
