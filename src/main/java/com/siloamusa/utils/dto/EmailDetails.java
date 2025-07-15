package com.siloamusa.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDetails {
    private String from;
    private String[] recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
