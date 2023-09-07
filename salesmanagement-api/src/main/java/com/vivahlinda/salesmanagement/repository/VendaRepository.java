package com.vivahlinda.salesmanagement.repository;

import com.vivahlinda.salesmanagement.domain.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

    List<Venda> getAllVenda();

    List<Venda> getVendaByUsername(@Param("username") String username);

    @Query("SELECT MONTH(v.dataVenda) as mes, SUM(v.totalCompra) as totalVendas FROM Venda v GROUP BY MONTH(v.dataVenda) ORDER BY mes")
    List<Object[]> getVendasMensais();



}
