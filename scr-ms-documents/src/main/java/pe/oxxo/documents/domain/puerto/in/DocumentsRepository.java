package pe.oxxo.documents.domain.puerto.in;

import pe.oxxo.documents.domain.model.Documents;

import java.util.Optional;


public interface DocumentsRepository {

    Documents saveDocuments(Documents documents);

    Optional<Documents> getDocuments(Integer id);

    Iterable<Documents> getDocuments( final String token);

    void deleteDocumentById(Integer id);
}
