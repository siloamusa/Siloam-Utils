package com.siloamusa.utils.generics;

import com.siloamusa.utils.dto.ParametroFuncional;
import com.siloamusa.utils.dto.ParametrosFuncionales;

public class UtilsParamFunctional {

    /*
     * Returns the value of a parameter from the given ParametrosFuncionales object.
     * If the parameter is not found, it returns an empty string.
     */
	public static String getParamValue(ParametrosFuncionales parmsFunct, String group, String code) {
		String value = "";
		for (ParametroFuncional param : parmsFunct.getCamposSolicitud()) {
			if (param.getGroup().equals(group) && param.getCode().equals(code)) {
				value = param.getCodeValue();
				break;
			}
		}
		return value;
	}

   
}
