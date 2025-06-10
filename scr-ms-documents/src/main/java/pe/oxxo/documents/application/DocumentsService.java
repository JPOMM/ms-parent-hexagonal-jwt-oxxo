package pe.oxxo.documents.application;

import pe.oxxo.documents.domain.model.Documents;

public interface DocumentsService {

    Documents save(Documents documents);

    Documents getDocumentsById(Integer id);

    /**
     * Method used to get an order by id.
     *
     * @param token the order id.
     * @return a {@link Iterable} containing an object of {@link Documents}.
     */
    Iterable<Documents> getDocuments(final String token);

    void deleteDocument(Integer id);

}
