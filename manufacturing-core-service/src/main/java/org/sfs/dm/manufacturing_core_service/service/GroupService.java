package org.sfs.dm.manufacturing_core_service.service;

import org.sfs.dm.manufacturing_core_service.entity.GroupEntity;
import org.sfs.dm.manufacturing_core_service.model.GroupEntityModel;
import org.sfs.dm.manufacturing_core_service.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<GroupEntityModel> getAllGroups() {
        List<GroupEntity> entities = groupRepository.findAll();
        return entities.stream()
                .map(this::toModel)
                .toList();
    }

    public ResponseEntity<GroupEntityModel> getGroupById(Long id) {
        Optional<GroupEntity> group = groupRepository.findById(id);
        return group.map(e -> ResponseEntity.ok(toModel(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public GroupEntityModel createGroup(GroupEntityModel groupModel) {
        GroupEntity entity = toEntity(groupModel);
        GroupEntity saved = groupRepository.save(entity);
        return toModel(saved);
    }

    public ResponseEntity<GroupEntityModel> updateGroup(Long id, GroupEntityModel groupDetails) {
        Optional<GroupEntity> group = groupRepository.findById(id);
        if (group.isPresent()) {
            GroupEntity updatedGroup = group.get();
            updatedGroup.setName(groupDetails.getName());
            GroupEntity saved = groupRepository.save(updatedGroup);
            return ResponseEntity.ok(toModel(saved));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteGroup(Long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private GroupEntityModel toModel(GroupEntity entity) {
        GroupEntityModel model = new GroupEntityModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        return model;
    }

    private GroupEntity toEntity(GroupEntityModel model) {
        GroupEntity entity = new GroupEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        return entity;
    }

}
