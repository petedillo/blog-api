package com.petedillo.api.dto;

import com.petedillo.api.model.BlogMedia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoverImageDTO {
    private String url;
    private String altText;

    public static CoverImageDTO fromEntity(BlogMedia media) {
        if (media == null) return null;
        return new CoverImageDTO(media.getUrl(), media.getAltText());
    }
}
