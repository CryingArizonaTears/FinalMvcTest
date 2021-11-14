package com.senla.service;

import com.senla.api.dao.IAdDao;
import com.senla.api.dao.IMaintenanceDao;
import com.senla.api.service.IMaintenanceService;
import com.senla.model.Ad;
import com.senla.model.Maintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Service
public class MaintenanceService implements IMaintenanceService {
    @Autowired
    private IMaintenanceDao maintenanceDao;
    @Autowired
    private IAdDao adDao;

    @Override
    public void createMaintenance(String name, String description, Double price, Integer plusDays) {

        Maintenance maintenance = new Maintenance();
        maintenance.setName(name);
        maintenance.setDescription(description);
        maintenance.setPrice(price);
        maintenance.setPlusDays(plusDays);
        maintenanceDao.save(maintenance);
    }


    @Override
    public void addMaintenanceToAd(Long adId, Long maintenanceId) {

        Ad ad = adDao.get(adId);
        Maintenance maintenance = maintenanceDao.get(maintenanceId);
        ad.getMaintenances().add(maintenance);
        if (ad.getPremiumUntilDate() == null || ad.getPremiumUntilDate().isBefore(LocalDate.now())) {
            ad.setPremiumUntilDate(LocalDate.now().plusDays(maintenance.getPlusDays()));
        } else {
            ad.setPremiumUntilDate(ad.getPremiumUntilDate().plusDays(maintenance.getPlusDays()));
        }
        adDao.update(ad);
    }

}
