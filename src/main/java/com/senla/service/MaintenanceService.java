package com.senla.service;

import com.senla.api.dao.IAdDao;
import com.senla.api.dao.IMaintenanceDao;
import com.senla.api.service.IMaintenanceService;
import com.senla.model.Ad;
import com.senla.model.Maintenance;
import com.senla.model.dto.MaintenanceDto;
import com.senla.model.dto.filter.MaintenanceFilter;
import com.senla.modelMapperMethods.ModelMapperMapList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class MaintenanceService implements IMaintenanceService {
    @Autowired
    private IMaintenanceDao maintenanceDao;
    @Autowired
    private IAdDao adDao;
    @Autowired
    private ModelMapperMapList modelMapper;

    @Override
    public void createMaintenance(MaintenanceDto maintenanceDto) {

        Maintenance maintenance = modelMapper.map(maintenanceDto, Maintenance.class);
        maintenanceDao.save(maintenance);
    }

    @Override
    public List<MaintenanceDto> getByFilter(MaintenanceFilter maintenanceFilter) {
        return modelMapper.mapList(maintenanceDao.getByFilter(maintenanceFilter), MaintenanceDto.class);
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
