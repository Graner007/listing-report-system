package com.codecool.report.model.marketplace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Marketplace {

    private long id;

    private MarketplaceName marketplaceName;
}
