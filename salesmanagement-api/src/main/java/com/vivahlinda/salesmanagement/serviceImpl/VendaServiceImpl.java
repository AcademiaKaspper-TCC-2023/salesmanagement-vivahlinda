package com.vivahlinda.salesmanagement.serviceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vivahlinda.salesmanagement.JWT.JwtFilter;
import com.vivahlinda.salesmanagement.constants.VivahLindaConstants;
import com.vivahlinda.salesmanagement.domain.Venda;
import com.vivahlinda.salesmanagement.repository.VendaRepository;
import com.vivahlinda.salesmanagement.service.VendaService;
import com.vivahlinda.salesmanagement.utils.VivahLindaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class VendaServiceImpl implements VendaService {

    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    JwtFilter jwtFilter;


    @Override
    public ResponseEntity<String> gerarRelatorio(Map<String, Object> requestMap) {
        log.info("gerarRelatorio: Iniciado geração de relatorio, {}", requestMap);

        try {
            String nomeArquivo;
            if (validateRequestMap(requestMap)) {
                if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")) {
                    nomeArquivo = (String) requestMap.get("uuid");
                } else {
                    nomeArquivo = VivahLindaUtils.getUUID();
                    requestMap.put("uuid", nomeArquivo);
                    insertVenda(requestMap);
                }

                String dados = "Cliente: " + requestMap.get("nomeCliente") + "\n" +
                        "Número de Contato: " + requestMap.get("numeroContato") + "\n" +
                        "CPF: " + requestMap.get("cpfCliente") + "\n" +
                        "E-mail: " + requestMap.get("emailCliente") + "\n" +
                        "Contato: " + requestMap.get("noContatoCliente") + "\n" +
                        "Forma de pagamento: " + requestMap.get("formaPagamento") + "\n" +
                        "Vendedor: " + requestMap.get("loginVendedor") + "\n\n";

                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(VivahLindaConstants.LOCAL_ARQUIVOS_NFE + "\\" + nomeArquivo + ".pdf"));
                log.info("Novo documento " + (String) requestMap.get("uuid") + " gerado na pasta " + VivahLindaConstants.LOCAL_ARQUIVOS_NFE);

                documento.open();

                setRetangleInPdf(documento);

                // Adiciona o título ao documento
                Paragraph titulo = new Paragraph(VivahLindaConstants.TITULO, getFont("Header"));
                titulo.setAlignment(Element.ALIGN_CENTER);
                documento.add(titulo);

                // Adiciona os dados ao documento
                Paragraph paragraph = new Paragraph(dados + "\n \n", getFont("Dados"));
                documento.add(paragraph);

                // Cria uma tabela com 5 colunas
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);

                // Adiciona o cabeçalho da tabela
                addTableHeader(table);

                // Converte a string de detalheProdutosVendidos em um JSONArray
                JSONArray jsonArray = VivahLindaUtils.getJsonArrayFromString((String) requestMap.get("detalheProdutosVendidos"));

                // Adiciona as linhas à tabela a partir do JSONArray
                for (int i = 0; i < jsonArray.length(); i++) {
                    addLinhasNaTabela(table, VivahLindaUtils.getMapFromJson(jsonArray.getString(i)));
                }

                // Adiciona a tabela ao documento
                documento.add(table);

                // Adiciona o rodapé ao documento
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dataHoraImpressao = sdf.format(new Date());
                Paragraph footer = new Paragraph("\n\n\n\n\nTotal: R$ " + requestMap.get("totalCompra") + "\n"
                        + "OBRIGADO PELA PREFERÊNCIA!" + "\n" + VivahLindaUtils.getDataHoraAtualFormatada() //TODO: AJUSTAR ESSA HORA FIXA
                        + "\nHorário da impressão do documento: " + dataHoraImpressao, getFont("Dados"));
                documento.add(footer);

                documento.close();

                return new ResponseEntity<>("{\"uuid\":\"" + nomeArquivo + "\"}", HttpStatus.OK);
            }
            return VivahLindaUtils.getResponseEntity(VivahLindaConstants.DADOS_NAO_ENCONTRADO, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Venda>> getVenda() {
        List<Venda> lista = new ArrayList<>();
        if (jwtFilter.isAdmin()) {
            lista = vendaRepository.getAllVenda();
        } else {
            lista = vendaRepository.getVendaByUsername(jwtFilter.getUsuarioAtual());

        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Gerando PDF: getPdf : {}", requestMap);
        try {
            //byte tamanho 0
            byte[] byteArray = new byte[0];

            if (!requestMap.containsKey("uuid") && validateRequestMap(requestMap))
                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);

            String caminhoArquivo = VivahLindaConstants.LOCAL_ARQUIVOS_NFE + "\\" + (String) requestMap.get("uuid") + ".pdf";

            if (VivahLindaUtils.isArquivoExiste(caminhoArquivo)) {
                // Obtém os bytes do arquivo PDF e atribui ao array de bytes
                byteArray = getByteArray(caminhoArquivo);

                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            } else {
                requestMap.put("isGenerate", false);
                log.info("GERANDO UM NOVO ARQUIVO PDF : {}", requestMap.get("uuid"));
                gerarRelatorio(requestMap);
                byteArray = getByteArray(caminhoArquivo);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteVenda(Integer id) {
        try {
            Optional optional = vendaRepository.findById(id);
            if (!optional.isEmpty()) {
                vendaRepository.deleteById(id);
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.DELETADO_SUCESSO, HttpStatus.OK);
            } else {
                return VivahLindaUtils.getResponseEntity(VivahLindaConstants.VENDA_NOTFOUND, HttpStatus.OK);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return VivahLindaUtils.getResponseEntity(VivahLindaConstants.ALGO_DEU_ERRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Método privado para obter os bytes do arquivo PDF
    private byte[] getByteArray(String caminhoArquivo) throws Exception {
        // Cria um objeto File com base no caminho do arquivo PDF
        File arquivoInicial = new File(caminhoArquivo);

        // Abre um fluxo de entrada para ler o arquivo PDF
        InputStream targetStream = new FileInputStream(arquivoInicial);

        // Lê os bytes do fluxo de entrada e armazena no array de bytes
        byte[] byteArray = IOUtils.toByteArray(targetStream);

        // Fecha o fluxo de entrada
        targetStream.close();

        // Retorna o array de bytes contendo o conteúdo do arquivo PDF
        return byteArray;
    }

    // Método para adicionar as linhas na tabela do PDF a partir de um Map de dados
    private void addLinhasNaTabela(PdfPTable table, Map<String, Object> dados) {
        log.info("Dentro do metodo addLinhasNaTabela{}", dados);
        table.addCell((String) dados.get("nome"));
        table.addCell((String) dados.get("categoria"));

    // Essa linha de código adiciona o valor da coluna "quantidade" como uma célula à tabela no formato de texto, convertendo-o para uma string antes de fazer a adição.
        table.addCell(String.valueOf(dados.get("quantidade")));
        table.addCell(Double.toString((Double) dados.get("preco")));
        table.addCell(Double.toString((Double) dados.get("total")));
    }

    // Método para adicionar o cabeçalho da tabela do PDF
    private void addTableHeader(PdfPTable table) {
        log.info("Dentro do metodo addTableHeader");
        Stream.of("Nome", "Categoria", "Quantidade", "Preço", "Sub Total")
                .forEach(tituloColuna -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(tituloColuna));
                    header.setBackgroundColor(BaseColor.WHITE);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private void setRetangleInPdf(Document documento) throws DocumentException {
        log.info("Dentro do metodo setRetangleInPdf");
        // Cria um retângulo nas coordenadas (18, 15) com largura 577 e altura 825.
        Rectangle rectangle = new Rectangle(577, 825, 18, 15);
        // Habilita os lados do retângulo.
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1); // Define a largura da borda do retângulo como 1.
        documento.add(rectangle); // Adiciona o retângulo ao documento PDF.
    }

    private Font getFont(String type) {
        log.info("Dentro do metodo getFont");
        switch (type) {
            case "Header":
                // Define a fonte como "HELVETICA_BOLDOBLIQUE" com tamanho 18 e cor preta (BaseColor.BLACK).
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD); // Define o estilo da fonte como negrito.
                return headerFont; // Retorna a fonte criada para o cabeçalho.
            case "Dados":
                // Define a fonte como "TIMES_ROMAN" com tamanho 11 e cor preta (BaseColor.BLACK).
                Font dadosFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dadosFont.setStyle(Font.BOLD); // Define o estilo da fonte como negrito.
                return dadosFont; // Retorna a fonte criada para os dados.
            default:
                // Caso o tipo não seja "Header" ou "Dados", retorna uma fonte padrão (Font).
                return new Font();
        }
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("nomeCliente") &&
                requestMap.containsKey("cpfCliente") &&
                requestMap.containsKey("emailCliente") &&
                requestMap.containsKey("noContatoCliente") &&
                requestMap.containsKey("formaPagamento") &&
                requestMap.containsKey("totalCompra") &&
                requestMap.containsKey("detalheProdutosVendidos");
    }

    private void insertVenda(Map<String, Object> requestMap) {
        try {
            Venda venda = new Venda();

            venda.setUuid((String) requestMap.get("uuid"));
            venda.setNomeCliente((String) requestMap.get("nomeCliente"));
            venda.setCpfCliente((String) requestMap.get("cpfCliente"));
            venda.setEmailCliente((String) requestMap.get("emailCliente"));
            venda.setNoContatoCliente((String) requestMap.get("noContatoCliente"));
            venda.setFormaPagamento((String) requestMap.get("formaPagamento"));

            BigDecimal totalCompra = VivahLindaUtils.converteValorBigdecimal((String) requestMap.get("totalCompra"));
            venda.setTotalCompra(totalCompra);

            venda.setDetalheProdutosVendidos((String) requestMap.get("detalheProdutosVendidos"));

            String nomeVendedor = jwtFilter.getUsuarioAtual();

            requestMap.put("loginVendedor", nomeVendedor);

            venda.setLoginVendedor(nomeVendedor);

            venda.setDataVenda(LocalDateTime.now());
            vendaRepository.save(venda);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
