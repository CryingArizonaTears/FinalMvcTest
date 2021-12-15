package com.senla.service;

import com.senla.api.dao.IAdDao;
import com.senla.api.dao.IMaintenanceDao;
import com.senla.api.service.IMaintenanceService;
import com.senla.api.service.IUserService;
import com.senla.model.Ad;
import com.senla.model.Maintenance;
import com.senla.model.Role;
import com.senla.model.UserProfile;
import com.senla.model.dto.MaintenanceDto;
import com.senla.model.dto.filter.MaintenanceFilter;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Log4j
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
    private IUserService userService;

    @Override
    public void createMaintenance(MaintenanceDto maintenanceDto) {
        log.debug("Method: createMaintenance, входящий: " + maintenanceDto.toString());
        Maintenance maintenance = modelMapper.map(maintenanceDto, Maintenance.class);
        log.debug("Method: createMaintenance, выходящий: " + maintenance.toString());
        maintenanceDao.save(maintenance);
    }

    @Override
    public List<MaintenanceDto> getByFilter(MaintenanceFilter maintenanceFilter) {
        log.debug("Method: getByFilter, входящий: " + maintenanceFilter.toString());
        List<MaintenanceDto> maintenanceDtos = modelMapper.mapList(maintenanceDao.getByFilter(maintenanceFilter), MaintenanceDto.class);
        log.debug("Method: getByFilter, выходящий: " + maintenanceDtos.toString());
        return maintenanceDtos;
    }

    @Override
    public void addMaintenanceToAd(Long adId, MaintenanceDto maintenanceDto) {
        log.debug("Method: addMaintenanceToAd, входящий id:" + adId + "входящий: " + maintenanceDto.toString());
        UserProfile userProfile = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
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
                log.debug("Method: addMaintenanceToAd, выходящий: " + ad.toString());
                adDao.update(ad);
            } else {
                throw new SecurityException("Вы не можете редактировать чужие штуки");
            }
        }
        if (userProfile.getRole().equals(Role.ROLE_ADMIN)) {
            log.debug("Method: addMaintenanceToAd, выходящий: " + ad.toString());
            adDao.update(ad);
        }

    }

}
