package com.petedillo.api.dto;

import com.petedillo.api.model.BlogMedia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
    private Long id;
    private String type;
    private String url;
    private String altText;
    private String caption;
    private Integer displayOrder;

    public static MediaDTO fromEntity(BlogMedia media) {
        if (media == null) return null;
        
        return new MediaDTO(
            media.getId(),
            media.getMediaType().name(),
            media.getUrl(), // Uses getUrl() helper method
            media.getAltText(),
            media.getCaption(),
            media.getDisplayOrder()
        );
    }
}
