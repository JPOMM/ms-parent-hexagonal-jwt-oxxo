package pe.oxxo.documents.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Documents {

    private Long id;
    private String title;
    private String type;
    private String author;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private boolean published;

}
