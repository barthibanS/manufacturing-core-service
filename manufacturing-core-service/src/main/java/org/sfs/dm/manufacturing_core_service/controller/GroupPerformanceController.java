package org.sfs.dm.manufacturing_core_service.controller;

import org.sfs.dm.manufacturing_core_service.dto.GroupPerformanceDTO;
import org.sfs.dm.manufacturing_core_service.service.GroupPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
public class GroupPerformanceController {

    @Autowired
    private GroupPerformanceService groupPerformanceService;

    @GetMapping("/{groupId}/performance")
    public ResponseEntity<GroupPerformanceDTO> getGroupPerformance(@PathVariable Long groupId) {
        GroupPerformanceDTO dto = groupPerformanceService.getGroupPerformance(groupId);
        return ResponseEntity.ok(dto);
    }
} 