package com.project.bcl_back.dto.trainer.response;

import com.project.bcl_back.entity.TrainerInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrainerListResponseDto {
    private Long trainerId;
    private String name;
    private String shortIntroduce;
    private String jobAddress;
    private String profileImage;

    public TrainerListResponseDto(TrainerInfo trainer) {
        this.trainerId = trainer.getId();
        this.name = trainer.getUser().getName();
        this.jobAddress = trainer.getJobAddress();
        this.shortIntroduce = trainer.getShortIntroduce();
        this.profileImage = trainer.getUser().getProfileImage() != null
                ? trainer.getUser().getProfileImage().getFullUrl()
                : null;
    }
}
