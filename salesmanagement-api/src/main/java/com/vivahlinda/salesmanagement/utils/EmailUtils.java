package com.vivahlinda.salesmanagement.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender; // enviar e-mails

    @Value("${spring.mail.username}")
    private String remetente; // sender

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtils.class);

    public void sendSimpleMessage(String para, String assunto, String texto, List<String> lista) {
        // Cria uma instância de SimpleMailMessage para construir o e-mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(remetente); // Define o remetente do e-mail como o e-mail da empresa
        message.setTo(para); // destinatário do e-mail
        message.setSubject(assunto);
        message.setText(texto);
        LOGGER.info("Mensagem do email criada");

        // Se a lista de destinatários em cópia (cc) não for vazia, adiciona os destinatários cc ao e-mail
        if (lista != null && lista.size() > 0)
            message.setCc(getCcArray(lista));

        javaMailSender.send(message);
        LOGGER.info("E-mail enviado para: {}", lista.toString());
    }

    // Método privado para converter a lista de destinatários em cópia (cc) em um array de strings.
    private String[] getCcArray(List<String> ccLista) {
        String[] cc = new String[ccLista.size()];
        for (int i = 0; i < ccLista.size(); i++) {
            cc[i] = ccLista.get(i);
        }
        return cc;
    }

    public void enviarEmailRecuperarSenha(String para, String assunto, String senha) throws MessagingException {
        MimeMessage mensagem = javaMailSender.createMimeMessage();
        MimeMessageHelper dados = new MimeMessageHelper(mensagem, true);
        dados.setFrom(remetente);
        dados.setTo(para);
        dados.setSubject(assunto);

        String corpoEmail = "<html><body>" +
                "<p>Olá,</p>" +
                "<p>Recebemos uma solicitação para redefinir sua senha na Vivah Linda Store. Aqui estão os detalhes:</p>" +
                "<ul>" +
                "<li><strong>Email:</strong> " + para + "</li>" +
                "<li><strong>Senha:</strong> " + senha + "</li>" +
                "</ul>" +
                "<p>Recomendamos que você faça login usando sua nova senha e, em seguida, atualize-a para uma senha segura de sua escolha. Clique no link abaixo para acessar a loja:</p>" +
                "<p><a href=\"http://www.vivahlinda.com/\">Acessar Vivah Linda Store</a></p>" +
                "<p>Se você não solicitou essa redefinição de senha ou tem alguma dúvida, por favor, entre em contato com nossa equipe de suporte.</p>" +
                "<p>Obrigado,</p>" +
                "<p>Equipe Vivah Linda Store</p>" +
                "</body></html>";

        dados.setText(corpoEmail, true); // Define o conteúdo como HTML
        javaMailSender.send(mensagem);
    }
}
