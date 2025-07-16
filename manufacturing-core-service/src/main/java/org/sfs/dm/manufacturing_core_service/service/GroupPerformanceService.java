package org.sfs.dm.manufacturing_core_service.service;

import org.sfs.dm.manufacturing_core_service.dto.GroupPerformanceDTO;
import org.sfs.dm.manufacturing_core_service.entity.*;
import org.sfs.dm.manufacturing_core_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GroupPerformanceService {
    private static final Logger log = LoggerFactory.getLogger(GroupPerformanceService.class);
    @Autowired private GroupDeviceRepository groupDeviceRepo;
    @Autowired private PersonGroupRepository personGroupRepo;
    @Autowired private LoginSessionRepository loginSessionRepo;
    @Autowired private DeviceRepository deviceRepo;

    public GroupPerformanceDTO getGroupPerformance(Long groupId) {
        log.info("Calculating group performance for groupId={}", groupId);

        // Get all devices for the group
        List<GroupDevice> groupDevices = groupDeviceRepo.findByGroupId(groupId);
        List<Long> deviceIds = groupDevices.stream()
                .map(gd -> gd.getDevice().getDeviceId())
                .toList();
        log.debug("Found {} devices for group {}: {}", deviceIds.size(), groupId, deviceIds);

        // Get all group members
        List<PersonGroup> personGroups = personGroupRepo.findByGroupId(groupId);
        List<Long> personIds = personGroups.stream()
                .map(pg -> pg.getPerson().getId())
                .toList();
        int groupSize = personIds.size();
        log.debug("Found {} persons for group {}: {}", groupSize, groupId, personIds);

        // Get all login sessions for these devices and people
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime since = now.minusHours(24);
        List<LoginSession> allSessions = deviceIds.isEmpty() || personIds.isEmpty() ?
                Collections.emptyList() :
                loginSessionRepo.findByDeviceDeviceIdInAndPersonIdIn(deviceIds, personIds);
        log.debug("Fetched {} login sessions for group {} in the last 24 hours", allSessions.size(), groupId);

        List<GroupPerformanceDTO.DeviceStats> deviceStatsList = new ArrayList<>();
        for (Long deviceId : deviceIds) {
            // Total operation time for this device by group members
            long totalSeconds = allSessions.stream()
                    .filter(ls -> ls.getDevice().getDeviceId().equals(deviceId))
                    .mapToLong(ls -> {
                        LocalDateTime login = ls.getLoginTime();
                        LocalDateTime logout = ls.getLogoutTime() != null ? ls.getLogoutTime() : now;
                        return Duration.between(login, logout).getSeconds();
                    })
                    .sum();
            log.debug("Device {}: total operation time (seconds) = {}", deviceId, totalSeconds);

            // Utilization rate for last 24h
            // For each minute in the last 24h, count group members logged in, average over 24h
            int minutes = 24 * 60;
            double utilizationSum = 0;
            for (int i = 0; i < minutes; i++) {
                LocalDateTime t = since.plusMinutes(i);
                long loggedIn = allSessions.stream()
                        .filter(ls -> ls.getDevice().getDeviceId().equals(deviceId))
                        .filter(ls -> !ls.getLoginTime().isAfter(t) && (ls.getLogoutTime() == null || ls.getLogoutTime().isAfter(t)))
                        .map(ls -> ls.getPerson().getId())
                        .distinct()
                        .count();
                utilizationSum += groupSize == 0 ? 0 : ((double) loggedIn / groupSize) * 100.0;
            }
            double utilizationRate = minutes == 0 ? 0 : utilizationSum / minutes;
            log.debug("Device {}: utilization rate (%%) = {}", deviceId, utilizationRate);

            deviceStatsList.add(new GroupPerformanceDTO.DeviceStats(deviceId, totalSeconds, utilizationRate));
        }
        log.info("Completed group performance calculation for groupId={}", groupId);
        return new GroupPerformanceDTO(groupId, deviceStatsList);
    }
} 