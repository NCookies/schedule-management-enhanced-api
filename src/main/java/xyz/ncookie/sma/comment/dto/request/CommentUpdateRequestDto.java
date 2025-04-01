package xyz.ncookie.sma.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentUpdateRequestDto(
        @NotBlank @Size(max = 255) String contents
) {
}
