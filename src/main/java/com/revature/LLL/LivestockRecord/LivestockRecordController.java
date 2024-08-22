package com.revature.LLL.LivestockRecord;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicalRecord")
public class LivestockRecordController {
    private final LivestockRecordService livestockRecordService;

    @Autowired
    public LivestockRecordController(LivestockRecordService livestockRecordService) {
        this.livestockRecordService = livestockRecordService;
    }

    /**
     * Get all livestock records for user
     * @param userId
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<List<LivestockRecord>> getLivestockRecordsByUserId(@Valid @RequestHeader int userId) {
        if (userId == 0) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(livestockRecordService.getLivestockRecords(userId));
        }
    }

    /**
     * Get all livestock records for a livestock
     * @param livestockId
     * @return
     */
    @GetMapping("/livestock")
    public ResponseEntity<List<LivestockRecord>> getLivestockRecordsByLivestockId(@RequestParam int livestockId) {
        if (livestockId == 0) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(livestockRecordService.getLivestockRecordsByLivestockId(livestockId));
        }
    }

}
