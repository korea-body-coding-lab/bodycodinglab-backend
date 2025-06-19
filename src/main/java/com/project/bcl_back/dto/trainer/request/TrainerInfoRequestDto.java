package com.project.bcl_back.dto.trainer.request;

import com.project.bcl_back.common.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Date;
@Getter
@Builder
@AllArgsConstructor
public class TrainerInfoRequestDto {
    private Long id;
    private String jobAddress;
    private String shortIntroduce;
    private String longIntroduce;
    private MultipartFile file;
    private String educationName;
    private String educationEntrance;
    private String educationGraduate;

    public LocalDateTime getEntranceDateTime() {
        return DateUtils.yearDateParse(this.educationEntrance);
    }

    public LocalDateTime getGraduateDateTime() {
        return DateUtils.yearDateParse(this.educationGraduate);
    }
}
