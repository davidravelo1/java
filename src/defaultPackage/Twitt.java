package defaultPackage;

import java.awt.Panel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Twitt {
 
    
	public static void main(String arg[]) throws TwitterException, IOException {
            
		
		File input = new File("srcpages/index.html");
		Document doc = Jsoup.connect("https://es.wikipedia.org/wiki/Anexo:Libros_m%C3%A1s_vendidos").get();

                 
		
		String moreLikesTweet=null;
		String moreLikesUser=null;
		int likes=0;
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("2oyNn2o3uDESonxGxFxPGdsk2")
		  .setOAuthConsumerSecret("mXSZVGs8OVCIVpU2bKUubgvEvSCQ9Frh5P0Jq3gGJtXnn2f49c")
		  .setOAuthAccessToken("1234141760001118208-wWFl8VLMkq6omL66qKLl3eK5jCklyZ")
		  .setOAuthAccessTokenSecret("sAVd27BhDjoZJ6cZ3meJA1NFjWkqRAkUyESdpIWGzhjvF");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		
	    List<Status> statuses = twitter.getUserTimeline("udistrital", new Paging(1, 40));
	    System.out.println("Showing home timeline.");
	    for (Status status : statuses) {
                if(!status.isRetweet()){
                    System.out.println(status.getUser().getName() + ":" +
                                       status.getText());
                    System.out.println("Likes: "+status.getRetweetCount());
                    if(status.getRetweetCount()>likes) {
                            moreLikesTweet = status.getText();
                            moreLikesUser= status.getUser().getName();
                            likes=status.getRetweetCount();           
                    }
                }
	    }
        System.out.println("---------------------------------------------------\n");   
            System.out.println(moreLikesUser + ":" + moreLikesTweet);
            System.out.println("Likes: "+likes);            
            
            
            
            final String username = "davidravelogcmsb@gmail.com ";
        final String password = "hola123";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("davidfravelogcmsb@gmail.com")
            );
            message.setSubject("David ravelo 20191020061");
            message.setText("El tweet con más likes es: "+moreLikesUser+" : "+moreLikesTweet +" n° Likes: "+likes);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
}
