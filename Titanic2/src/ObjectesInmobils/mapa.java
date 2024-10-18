package ObjectesInmobils;
import java.awt.Desktop;  
import java.io.*;
public class mapa extends mobles {
     public mapa(){
      super("mapa");
     }

     public void llegirMapa(){
          try {  
               File file = new File("Titanic2\\mapaProjecte-Model 20.48.55.pdf");   
               if(!Desktop.isDesktopSupported()){  
                    System.out.println("No tens cap aplicació amb la que obrir els pdfs");  
                    return;  
               }  
               Desktop desktop = Desktop.getDesktop();  
               if(file.exists())     
               desktop.open(file);   
          }  
          catch(Exception e)  
          {  
               System.out.println("No tens cap aplicació amb la que obrir els pdfs");   
          }  
     }  
}

