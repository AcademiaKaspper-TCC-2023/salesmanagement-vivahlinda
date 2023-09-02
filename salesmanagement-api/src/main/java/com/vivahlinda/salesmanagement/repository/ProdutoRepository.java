package com.vivahlinda.salesmanagement.repository;

import com.vivahlinda.salesmanagement.domain.Produto;
import com.vivahlinda.salesmanagement.domain.dtos.ProdutoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<ProdutoDTO> getAllProduto();

    @Modifying
    @Transactional
    Integer updateProdutoStatus(@Param("status") String status, @Param("id") Integer id);

    List<ProdutoDTO> getProdutoByCategoria(@Param("id") Integer id);

    ProdutoDTO getProdutoById(@Param("id") Integer id);
}
