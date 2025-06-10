package pe.oxxo.documents.application;

import com.oxxo.scr.ms.common.event.models.RoleResponseDto;
import com.oxxo.scr.ms.common.utils.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.oxxo.documents.domain.model.Documents;
import pe.oxxo.documents.domain.puerto.in.DocumentsRepository;
import pe.oxxo.documents.infrastructure.client.out.MaintenanceClientRest;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor
public class DocumentsServiceImpl implements DocumentsService {

    private final DocumentsRepository documentsRepository;

    private final MaintenanceClientRest maintenanceClientRest;

    @Override
    public Documents save(Documents documents) {
        Optional.ofNullable(documents.getId())
                .ifPresentOrElse(
                        id -> documents.setDateUpdated(LocalDateTime.now()),
                        () -> {
                            LocalDateTime now = LocalDateTime.now();
                            documents.setDateCreated(now);
                            documents.setDateUpdated(now);
                        }
                );
        return documentsRepository.saveDocuments(documents);
    }

    @Override
    public Documents getDocumentsById(Integer id) {
        return this.documentsRepository.getDocuments(id).get();
    }

    @Override
    public Iterable<Documents> getDocuments(final String token) {
        GenericResponse<RoleResponseDto> roleResponseDto = maintenanceClientRest.getRoleById("1");
        log.info("roleResponseDto"+roleResponseDto);
        return this.documentsRepository.getDocuments(token);
    }

    @Override
    public void deleteDocument(Integer id) {
        this.documentsRepository.deleteDocumentById(id);
    }
}
