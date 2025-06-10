package pe.oxxo.documents.infrastructure.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Response {
    private List<java.lang.Error> errors = new ArrayList<>();

    public void addError(Error error) {
        this.errors.add(error);
    }
}
