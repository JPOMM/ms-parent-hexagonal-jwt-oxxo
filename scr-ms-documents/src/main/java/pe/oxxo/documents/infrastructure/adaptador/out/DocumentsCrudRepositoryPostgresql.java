package pe.oxxo.documents.infrastructure.adaptador.out;

import org.springframework.data.repository.CrudRepository;
import pe.oxxo.documents.infrastructure.entity.DocumentsEntity;

public interface DocumentsCrudRepositoryPostgresql extends CrudRepository<DocumentsEntity, Integer> {
}
