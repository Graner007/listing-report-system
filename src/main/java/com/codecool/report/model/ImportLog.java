package com.codecool.report.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImportLog {

    private String plantId;

    private String marketplaceName;

    private String invalidField;
}
