package pe.oxxo.documents.infrastructure.adaptador.in;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pe.oxxo.documents.domain.model.Documents;
import pe.oxxo.documents.domain.puerto.in.DocumentsRepository;
import pe.oxxo.documents.infrastructure.adaptador.out.DocumentsCrudRepositoryPostgresql;
import pe.oxxo.documents.infrastructure.entity.DocumentsEntity;
import pe.oxxo.documents.infrastructure.exceptions.ResourceNotFoundException;
import pe.oxxo.documents.infrastructure.mapper.DocumentsMapper;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DocumentsRepositoryPostgresql  implements DocumentsRepository {

    private DocumentsMapper documentsMapper;

    private final DocumentsCrudRepositoryPostgresql documentsCrudRepositoryPostgresql;

    @Override
    public Documents saveDocuments(Documents documents) {
        DocumentsEntity documentsEntity = this.documentsMapper.toDocumentsEntity(documents);
        return this.documentsMapper.toDocument(this.documentsCrudRepositoryPostgresql.save(documentsEntity));
    }

    @Override
    public Optional<Documents> getDocuments(Integer id) {
        DocumentsEntity document = this.documentsCrudRepositoryPostgresql.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Recurso no encontrado")
        );
        return Optional.of(this.documentsMapper.toDocument(document));
    }

    @Override
    public Iterable<Documents> getDocuments(final String token) {
        return this.documentsMapper.toDocuments(this.documentsCrudRepositoryPostgresql.findAll());
    }

    @Override
    public void deleteDocumentById(Integer id) {
        DocumentsEntity documentsEntity = this.documentsCrudRepositoryPostgresql.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Recurso no encontrado")
        );
        this.documentsCrudRepositoryPostgresql.deleteById(Math.toIntExact(documentsEntity.getId()));
    }
}
