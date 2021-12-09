package com.senla.controllers;

import com.senla.api.service.IAdService;
import com.senla.api.service.ICommentService;
import com.senla.api.service.IMaintenanceService;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.CommentDto;
import com.senla.model.dto.MaintenanceDto;
import com.senla.model.dto.filter.AdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ads")
public class AdController {

    @Autowired
    private IAdService adService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IMaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<AdDto>> getAdsByFilter(AdFilter filter) {
        return ResponseEntity.ok(adService.getByFilter(filter));
    }

    @PostMapping
    public ResponseEntity<Void> createAd(@RequestBody AdDto adDto) {
        adService.createAd(adDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateAd(@RequestBody AdDto adDto) {
        adService.editAd(adDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        adService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comment")
    public ResponseEntity<Void> addComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/maintenance")
    public ResponseEntity<Void> addMaintenance(@PathVariable Long id,
                                               @RequestBody MaintenanceDto maintenanceDto) {
        maintenanceService.addMaintenanceToAd(id, maintenanceDto);
        return ResponseEntity.noContent().build();
    }
}
