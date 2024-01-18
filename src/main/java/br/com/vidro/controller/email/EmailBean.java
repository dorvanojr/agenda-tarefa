package br.com.vidro.controller.email;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.core.EmitUtils;
import br.com.vidro.entity.Email;
import br.com.vidro.entity.SendEmail;




@ConversationScoped
@Named
public class EmailBean implements Serializable {

	private static final long serialVersionUID = -1791949204232934815L;
	
	@Inject
	private SendEmail email;
    
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private Conversation conversation;

	
    @PostConstruct
		public void init() {
			log.info("Inicializando a conversacao no Bean "
					+ this.getClass().getCanonicalName());
			beginConversation();
		}
	 
	    private void beginConversation() {
			if (conversation.isTransient()) {
				conversation.begin();
				log.info("ConversaÃ§Ã£o iniciada - ID:" + conversation.getId());
			}
		}
	    @PreDestroy
	    public void endConversation() {
			if (!conversation.isTransient()) {
				System.out.println("Conversação encerrada, id: " + conversation.getId());
				conversation.end();
			}
		}
	
	public static final String SERVIDOR_DE_EMAIL = "smtp.gmail.com";  
	  
    public static final int SERVIDOR_PORTA = 587;  
  
    public static final String REMETENTE_EMAIL = "dorvanojr@gmail.com";  
  
    public static final String REMETENTE_SENHA = "srv15030";  

	
	
	
	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	


    
    public void sendMail2() throws Exception {  
  
    	   final String username="dorvanojr@gmail.com";  
           final String password="kalebe1988";  
           Properties prop=new Properties();  
           prop.put("mail.smtp.auth", "true");  
           prop.put("mail.smtp.host", "smtp.gmail.com");  
           prop.put("mail.smtp.port", "587");  
           prop.put("mail.smtp.starttls.enable", "true");  
           prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
           
           Session session = Session.getDefaultInstance(prop,new javax.mail.Authenticator() {  
           protected PasswordAuthentication getPasswordAuthentication() {  
               return new PasswordAuthentication(username, password);  
           }  
           });  
         try {  
            
             String htmlBody = "<strong>This is an HTML Message</strong>";  
             String textBody = email.getCaixatexto();  
             Message message = new MimeMessage(session);  
             message.setFrom(new InternetAddress(email.getEmail()));  
             message.setRecipients(Message.RecipientType.TO,  
             InternetAddress.parse("dorvanojr@gmail.com"));  
             message.setSubject(email.getAssunto() + " - " +email.getEmail());  
             
             MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();  
             mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");  
             mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");  
             mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");  
             mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");  
             mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");  
             CommandMap.setDefaultCommandMap(mc);  
           
             message.setContent(textBody, "text/html");  
          
             System.out.println("Enviando ...");  
             Transport.send(message);  
             System.out.println("Done");  
         } catch (MessagingException e) {  
           System.out.println("msg de erro: "+e.getMessage());  
           throw new RuntimeException();  
         }       
       }


	public SendEmail getEmail() {
		return email;
	}


	public void setEmail(SendEmail email) {
		this.email = email;
	}      

	
}
