package pe.oxxo.documents.infrastructure.client.out;

import com.oxxo.scr.ms.common.event.models.RoleResponseDto;
import com.oxxo.scr.ms.common.utils.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "scr-ms-maintenance", url ="http://localhost:9090/api/roles/")
public interface MaintenanceClientRest {

    @GetMapping("{id}")
    GenericResponse<RoleResponseDto> getRoleById(@PathVariable("id") String id);

}
