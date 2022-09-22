package com.company.dto.tag;

import com.company.enums.TagStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
    private Integer id;
    private String name;
    private TagStatus status;
    private LocalDateTime createdDate;
    Boolean visible;

    public TagDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
