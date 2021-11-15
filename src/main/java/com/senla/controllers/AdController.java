package com.senla.controllers;

import com.senla.api.service.IAdService;
import com.senla.api.service.ICommentService;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.CommentDto;
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

    @GetMapping()
    public ResponseEntity<List<AdDto>> getAds(@RequestParam(defaultValue = "all") String sort,
                                              @RequestParam(defaultValue = "0") Long id,
                                              @RequestParam(defaultValue = "0") Double from,
                                              @RequestParam(defaultValue = "0") Double to) {
        switch (sort) {
            case ("category"):
                return ResponseEntity.ok(adService.filterByCategory(id));
            case ("user"):
                return ResponseEntity.ok(adService.filterByUserId(id));
            case ("price"):
                return ResponseEntity.ok(adService.filterByPrice(from, to));
            default:
                return ResponseEntity.ok(adService.getCurrentAds());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdDto> getAdById(@PathVariable Long id) {
        return ResponseEntity.ok(adService.getById(id));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Void> createAd(@RequestBody AdDto adDto) {
        adService.createAd(adDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Void> updateAd(@PathVariable Long id, @RequestBody AdDto adDto) {
        adService.editAd(id, adDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        adService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/addComment")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id,
                                         @RequestBody CommentDto commentDto) {
        commentService.createComment(id, commentDto);
        return ResponseEntity.noContent().build();
    }
}
