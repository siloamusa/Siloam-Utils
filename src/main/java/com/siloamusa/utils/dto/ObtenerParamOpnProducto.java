package com.siloamusa.utils.dto;

import java.util.List;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class ObtenerParamOpnProducto {
    private String Correlacion;
    private int Idioma;
    private int Canal;
    private String IdentificadorProductoBanco;
    private String TipoConsulta;
    private List<ParametroFuncional> CamposSolicitud;
}
