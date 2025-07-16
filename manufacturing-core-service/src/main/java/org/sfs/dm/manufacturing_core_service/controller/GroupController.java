package org.sfs.dm.manufacturing_core_service.controller;

import org.sfs.dm.manufacturing_core_service.entity.GroupEntity;
import org.sfs.dm.manufacturing_core_service.model.GroupEntityModel;
import org.sfs.dm.manufacturing_core_service.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<GroupEntityModel> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupEntityModel> getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @PostMapping
    public GroupEntityModel createGroup(@RequestBody GroupEntityModel group) {
        return groupService.createGroup(group);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupEntityModel> updateGroup(@PathVariable Long id, @RequestBody GroupEntityModel group) {
        return groupService.updateGroup(id, group);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        return groupService.deleteGroup(id);
    }
}
