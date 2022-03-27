package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.entities.Mission;
import com.v4bot.ninth.floor.enums.MissionStatus;
import com.v4bot.ninth.floor.repositories.MissionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {
    private final MissionsRepository missionsRepository;

    public Mission getActiveMission(Long chatId) {
        List<Mission> missionList = missionsRepository.findAllByChatIdOrderByUpdatedAtDesc(chatId);
        if(missionList.isEmpty()) {
            return null;
        }
        return missionList.stream().filter(mission -> MissionStatus.Active.equals(mission.getStatus())).findFirst().orElse(null);
    }

    public MissionStatus getMissionStatusForChat(Long chatId) {
        List<Mission> missionList = missionsRepository.findAllByChatIdOrderByUpdatedAtDesc(chatId);
        if(missionList.isEmpty()) {
            return MissionStatus.Absent;
        }
        Optional<Mission> missionOpt = missionList.stream().filter(mission -> MissionStatus.Active.equals(mission.getStatus())).findFirst();
        return missionOpt.isPresent() ? missionOpt.get().getStatus() : MissionStatus.Finished;
    }
}
