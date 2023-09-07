package com.vivahlinda.salesmanagement.domain.dtos;

import java.math.BigDecimal;
import java.time.Month;

public class VendaMensalDTO {
    private Month mes;
    private BigDecimal totalVendas;

    public VendaMensalDTO(Month mes, BigDecimal totalVendas) {
        this.mes = mes;
        this.totalVendas = totalVendas;
    }

    public Month getMes() {
        return mes;
    }

    public BigDecimal getTotalVendas() {
        return totalVendas;
    }
}
