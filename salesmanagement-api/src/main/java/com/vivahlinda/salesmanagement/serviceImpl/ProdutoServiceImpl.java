package com.vivahlinda.salesmanagement.serviceImpl;

import com.vivahlinda.salesmanagement.JWT.JwtFilter;
import com.vivahlinda.salesmanagement.constants.VivahLindaConstants;
import com.vivahlinda.salesmanagement.domain.Categoria;
import com.vivahlinda.salesmanagement.domain.Produto;
import com.vivahlinda.salesmanagement.domain.dtos.ProdutoDTO;
import com.vivahlinda.salesmanagement.repository.ProdutoRepository;
import com.vivahlinda.salesmanagement.service.ProdutoService;
import com.vivahlinda.salesmanagement.utils.VivahLindaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public ResponseEntity<String> addNewProdruto(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProdutoMap(requestMap, false)) {
                    produtoRepository.save(getProdutoFromMap(requestMap, false));
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.PRODUTO_ADD_SUCESSO, HttpStatus.OK);
                }
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.DADOS_INVALIDOS, HttpStatus.BAD_REQUEST);
            } else {
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ACESSO_NEGADO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProdutoDTO>> getAllProduto() {
        try {
            return new ResponseEntity<>(produtoRepository.getAllProduto(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProdruto(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProdutoMap(requestMap, true)) {
                    Optional<Produto> optional = produtoRepository.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        Produto produto = getProdutoFromMap(requestMap, true);
                        produto.setStatus(optional.get().getStatus());
                        produtoRepository.save(produto);
                        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.PRODUTO_UPDATE_SUCESSO, HttpStatus.OK);
                    } else {
                        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ID_PROD_NAO_EXISTE, HttpStatus.OK);
                    }
                } else {
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.DADOS_INVALIDOS, HttpStatus.BAD_REQUEST);
                }
            } else {
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ACESSO_NEGADO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduto(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional optional = produtoRepository.findById(id);
                if (!optional.isEmpty()) {
                    produtoRepository.deleteById(id);
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.PRODUTO_EXCLUIDO, HttpStatus.OK);
                } else {
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ID_PROD_NAO_EXISTE_DEL, HttpStatus.OK);
                }
            } else {
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ACESSO_NEGADO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional optional = produtoRepository.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    produtoRepository.updateProdutoStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.STATUS_UPDATE_SUCESSO, HttpStatus.OK);

                } else {
                    return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ID_PROD_NAO_EXISTE_STATUS, HttpStatus.OK);
                }
            } else {
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ACESSO_NEGADO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProdutoDTO>> getByCategoria(Integer id) {
        try {
            return new ResponseEntity<>(produtoRepository.getProdutoByCategoria(id), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProdutoDTO> getProdutoById(Integer id) {
        try {
            return new ResponseEntity<>(produtoRepository.getProdutoById(id), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ProdutoDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateProdutoMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("nome")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Produto getProdutoFromMap(Map<String, String> requestMap, boolean isAdd) {
        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(requestMap.get("categoriaId")));

        Produto produto = new Produto();
        if (isAdd) {
            produto.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            produto.setStatus("true");
        }
        produto.setCategoria(categoria);
        produto.setNome(requestMap.get("nome"));
        produto.setDescricao(requestMap.get("descricao"));

        BigDecimal preco = VivahLindaUtils.converteValorBigdecimal(requestMap.get("preco"));
        produto.setPreco(preco);

        return produto;
    }
}
