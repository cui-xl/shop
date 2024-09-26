package com.cxl.elasticsearch;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Good {
    private Long id;
    private String name;
    private String src;
    private BigDecimal price;
    private Boolean isNew;
    private Boolean isHot;
    private Boolean isPublish;
    private Boolean active;
}
