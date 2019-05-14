/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psp6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PSP6 {

    /* **********************************************************************
     DETECCION OS La variable SystemFlag se pone a 1 si es windows, 2 si es
     unix/linux, 0 en cualquier otro caso.
     ************************************************************************/
    public static void sistema() {

        int SystemFlag;

        String sistema = System.getProperty("os.name");
        if (sistema.contains("indow")) {
            System.out.println("Detectado un sistema operativo windows\n");
            SystemFlag = 1;
        } else {
            if (sistema.contains("nix") | (sistema.contains("nux"))) {
                System.out.print("Se detecta SO unix/linux. Sistema operativo " + sistema + "solo soportado para la opcion 2");
                SystemFlag = 2;
            } else {
                System.out.print("No se reconoce este SO. Sistema operativo " + sistema + " no está soportado");
                SystemFlag = 0;
            }
        }
    }

    //DISEÑO DE EXPRESION REGULAR NOMBRE(8 caracteres en minusculas)  
    static Pattern identID = Pattern.compile("[a-z]{8}");

    //DISEÑO DE EXPRESION REGULAR ARCHIVO(De 1 a 8 caracteres, un punto, de 2 a 3 caracteres de extensión)  
    static Pattern identArchivo = Pattern.compile("[a-zA-Z]{1,8}\\.[a-zA-Z]{2,3}");

    public static void main(String[] args) {

        try {
            
            /* **************
            DEFINIENDO LOGGER    
            *****************/
            Logger logger = Logger.getLogger("LogError");
            FileHandler fh = new FileHandler("c:\\Log.txt", true);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

       //FUNCION PARA INDICAR EL SO
            sistema();

            // VARIABLES
            boolean paseUsuario = false;
            boolean paseArchivo = false;
            String usuario="";
            String archivo="";

            /* *****************
        BUCLE USUARIO REGEXP
        *******************/
            while (paseUsuario != true) {
                System.out.println("Indica tu usuario: (Deben ser 8 letras minúsculas, no se acepta otra cosa)");
                Scanner usu = new Scanner(System.in);
                usuario = usu.nextLine();

                Matcher Compara = identID.matcher(usuario);
                if (Compara.matches()) {
                    System.out.println("EL USUARIO ES VALIDO\n");
                    logger.log(Level.INFO, "{0} Se ha validado correctamente", usuario);
                    paseUsuario = true;
                } else {
                    System.out.println("EL USUARIO NO CUMPLE LOS REQUISITOS DE SEGURIDAD\n");
                    logger.log(Level.WARNING, "{0} No ha superado el requisito de seguridad para usuarios", usuario);
                }
            }

            System.out.println("BIENVENIDO: " + usuario + "\n");

            /* *****************************************************
            ANTES DE PEDIR ARCHIVO MOSTRAMOS ARCHIVOS DEL DIRECTORIO
            ********************************************************/

            /* ***********NOTA*************
            PARA SALIDA DEL COMANDO POR CMD           
            System.out.println(Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"dir\"")); 
            ********************************/

            /*SALIDA CONSOLA*/
            System.out.println("Listando contenido de directorio actual de trabajo !");
            String dirName = System.getProperty("user.dir");

            Files.list(new File(dirName).toPath())
                    .limit(10)
                    .forEach(path -> {
                        System.out.println(path);
                    });

            /* *******************
            BUCLE PIDIENDO ARCHIVO
            *********************/
            while (paseArchivo != true) {
                System.out.println("Indica un archivo para mostrar contenido (Debe ser de 1 a 8 caracteres, un punto y una extension de 3 caracteres");
                Scanner fil = new Scanner(System.in);
                archivo = fil.nextLine();

                //TEST VS REGEXP
                Matcher Compara = identArchivo.matcher(archivo);
                if (Compara.matches()) {
                    System.out.println("El formato de archivo es correcto\n");
                    paseArchivo = true;
                    logger.log(Level.INFO, "{0} Ha abierto un archivo {1}", new Object[]{usuario, archivo});
                } else {
                    System.out.println("El archivo no cumple los criterios que se indican\n");
                    logger.log(Level.WARNING, "{0} Ha intentado abrir archivo invalido {1}", new Object[]{usuario, archivo});
                }
            }
            
            /* ********************************
            SE MUESTRA EL CONTENIDO DEL ARCHIVO
            ***********************************/
            System.out.println("Mostrando contenido del archivo solicitado (Si existe)\n");
            
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            /* *******************
            GESTION DE EXCEPCIONES          
            **********************/
            
        } catch (IOException e) {
            System.err.println("Algo ha ido mal...");
        } catch (InputMismatchException e) {
            System.err.println("Error en el formato de los campos");
            Logger.getLogger(PSP6.class.getName()).log(Level.SEVERE, null, e);
        } catch (SecurityException e) {
            System.err.println("Excepcion de seguridad");
            Logger.getLogger(PSP6.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
