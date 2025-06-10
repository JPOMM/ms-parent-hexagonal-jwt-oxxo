package com.oxxo.scr.ms.common.event.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponseDto implements Serializable {

  private Integer id;

  private String nombre;

  private String descripcion;

  private Integer productId;
}
