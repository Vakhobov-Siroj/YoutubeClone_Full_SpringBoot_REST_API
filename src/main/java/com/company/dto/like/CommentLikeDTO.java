package com.company.dto.like;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDTO {
    @NotNull(message = "Comment id qani?")
    private Integer commentId;
}
