package ObjectesInmobils;
import java.awt.Desktop;  
import java.io.*;
import java.nio.file.Paths;  // Per crear rutes compatibles

public class mapa extends mobles {
    public mapa(){ 
        super("mapa");
    }

    public void llegirMapa(){
        try {  
            // Utilitza Paths.get per crear una ruta compatible amb qualsevol sistema operatiu
            File file = Paths.get("Titanic2", "mapaProjecte-Model 20.48.55.pdf").toFile();  
            
            if(!Desktop.isDesktopSupported()){  
                System.out.println("No tens cap aplicació amb la que obrir els PDFs");  
                return;  
            }  
            
            Desktop desktop = Desktop.getDesktop();  
            if(file.exists()) {    
                desktop.open(file);  
            } else {
                System.out.println("El fitxer no existeix: " + file.getAbsolutePath());
            }
        }  
        catch(Exception e) {  
            System.out.println("No tens cap aplicació amb la que obrir els PDFs o hi ha hagut un error.");   
        }  
    }  
}