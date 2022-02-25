package com.senla.service;

import com.senla.annotation.Logging;
import com.senla.api.dao.IAdDao;
import com.senla.api.dao.IMaintenanceDao;
import com.senla.api.service.IMaintenanceService;
import com.senla.api.service.IUserAuthenticationService;
import com.senla.model.Ad;
import com.senla.model.Maintenance;
import com.senla.model.Role;
import com.senla.model.UserProfile;
import com.senla.model.dto.MaintenanceDto;
import com.senla.model.dto.filter.MaintenanceFilter;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ExtendedModelMapper modelMapper;
    @Autowired
    private IUserAuthenticationService userAuthenticationService;

    @Override
    @Logging
    public void createMaintenance(MaintenanceDto maintenanceDto) {
        Maintenance maintenance = modelMapper.map(maintenanceDto, Maintenance.class);
        maintenanceDao.save(maintenance);
    }

    @Override
    @Logging
    public List<MaintenanceDto> getByFilter(MaintenanceFilter maintenanceFilter) {
        return modelMapper.mapList(maintenanceDao.getByFilter(maintenanceFilter), MaintenanceDto.class);
    }

    @Override
    @Logging
    public void addMaintenanceToAd(Long adId, MaintenanceDto maintenanceDto) {
        UserProfile userProfile = modelMapper.map(userAuthenticationService.getCurrentUserProfile(), UserProfile.class);
        Ad ad = adDao.get(adId);
        Maintenance maintenance = modelMapper.map(maintenanceDto, Maintenance.class);
        ad.getMaintenances().add(maintenance);
        if (ad.getPremiumUntilDate() == null || ad.getPremiumUntilDate().isBefore(LocalDate.now())) {
            ad.setPremiumUntilDate(LocalDate.now().plusDays(maintenance.getPlusDays()));
        } else {
            ad.setPremiumUntilDate(ad.getPremiumUntilDate().plusDays(maintenance.getPlusDays()));
        }
        if (userProfile.getRole().equals(Role.ROLE_USER)) {
            if (userProfile.getAds().stream().anyMatch(ad1 -> ad1.getId().equals(adId))) {
                adDao.update(ad);
            } else {
                throw new SecurityException("Вы не можете редактировать чужие штуки");
            }
        }
        if (userProfile.getRole().equals(Role.ROLE_ADMIN)) {
            adDao.update(ad);
        }

    }

}
