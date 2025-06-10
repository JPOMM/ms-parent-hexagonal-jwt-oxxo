package pe.oxxo.documents.infrastructure.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pe.oxxo.documents.domain.model.Documents;
import pe.oxxo.documents.infrastructure.entity.DocumentsEntity;

@Mapper(componentModel = "spring")
public interface DocumentsMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "author", target = "author"),
            @Mapping(source = "dateCreated", target = "dateCreated"),
            @Mapping(source = "dateUpdated", target = "dateUpdated")
            }
    )
    Documents toDocument(DocumentsEntity documentsEntity);

    Iterable<Documents> toDocuments(Iterable<DocumentsEntity> documentsEntities);

    @InheritInverseConfiguration
    DocumentsEntity toDocumentsEntity(Documents documents);
}
