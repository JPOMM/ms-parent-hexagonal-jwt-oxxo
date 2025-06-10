package pe.oxxo.documents.infrastructure.exceptions;

import lombok.Data;

@Data
public class Error extends java.lang.Error {
    private String code;
    private String message;

}
