# TAREA 6.1 #

La primera aplicación cumple todos los requisitos que se piden. 

- Gestión con logger de Warnings e Infos
- Pattern para usuario (8 caracteres minúsculas)
- Pattern para archivo (de 1 a 8 caracteres, un punto y de 2 a 3 caracteres para la extension.
- Se visualiza el contenido del archivo

**He agregado a mayores (Funcionalidades extra).**

- Detección de SO
- PRE - Listado antes de seleccionar el archivo.
- Código comentado para visualización del archivo vía CMD
- Sentencias con lectura de propiedades para el correcto desarrollo del apartado 6.2


# TAREA 6.2 #

## Introducción ##

Antes de nada vamos a agregar un poco de código al principio del programa para poder hacer el punto 2, consistente en politicas de acceso. Para ello vamos a agregar este código a fin de capturar las propiedades java.home y user.home

![Captura 0 de modificando aplicación](https://github.com/arkaland/PSP6/blob/master/imagenes/0_Agregando%20codigo%20para%20politicas.JPG)

Luego lo que hacemos es mover la aplicación que hemos hecho para el punto 6.1 FUERA del paquete de Netbeans PSP6, de esta forma podemos compilarlo y no será necesario incluir rutas o manifest para que funcione independientemente de la ruta de donde vayamos a ejecutarlo.

*También vemos que se ha eliminado la sentencia PACKAGE*

![Captura 1 de código sin paquete](https://github.com/arkaland/PSP6/blob/master/imagenes/1_Codigo%20sin%20paquete.JPG)


Ahora moveremos el archivo PSP6.java a un directorio para facilitar las pruebas.

![Captura 2 de moviendo fichero java](https://github.com/arkaland/PSP6/blob/master/imagenes/2_Moviendo%20fichero%20java.JPG)

Tras esto compilamos y ejecutamos el código con los siguientes comandos vía cmd:
> javac PSP6.java
>  
> java PSP6

También lanzaremos la aplicación con el siguiente comando:
> java -Djava=security.manager PSP6

![Captura 3 de compilar y ejecutar](https://github.com/arkaland/PSP6/blob/master/imagenes/3_Compilar%20y%20ejecutar.JPG)

Vemos que se leen todas las propiedades del archivo. Ejecutamos policytool y abrimos java.policy en la ruta actual de java.


![Captura 4 policytool y java.policy](https://github.com/arkaland/PSP6/blob/master/imagenes/4_policytool%20y%20java.policy.JPG)

Vemos que hay una entrada que es Codebase <All> que permite que todos los programas lean todas las propiedades. Si retiramos esa politica nuestro programa no podría leer propiedades sensibles.

![Captura 5 codebase all](https://github.com/arkaland/PSP6/blob/master/imagenes/5_Captura%20codebase%20all.JPG)




## Firmando fichero jar ##

Creamos el jar de nuestra aplicación a partir del .class con el siguiente comando

> jar cvf PSP6.jar PSP6.class

Y generamos las claves con el siguiente comando:

> keytool -genkey -alias firmar -keypass hola00 -keystore DAM -storepass distancia

![Captura 6 jar y keys generadas](https://github.com/arkaland/PSP6/blob/master/imagenes/6_jar%20y%20generar%20keys.JPG)


Ya podemos ***Firmar el fichero*** con el siguiente comando:

> jarsigner -keystore DAM -signedjar sPSP6.jar PSP6.jar firmar

*Nos pedirá la clave de storepass y la clave de keypass , por ese orden*

![Captura 7 jar signed](https://github.com/arkaland/PSP6/blob/master/imagenes/7_JAR%20signed.JPG)

Por último debemos exportar la llave publica para generar un archivo .cert el cual debemos enviar junto con nuestro jar a los clientes o destinatarios de la aplicación.

![Captura 8 exportando llave](https://github.com/arkaland/PSP6/blob/master/imagenes/8_Exportar%20llave%20publica.JPG)


## Solo lectura en C:\Datos ##

De nuevo ejecutamos: 

> policytool

y abrimos java.policy en la ruta indicada anteriormente
Tras esto solo tendríamos que borrar todas las entradas de Codebase (Dan permisos a otras rutas) y la de CodeBase All (Da un listado de permisos sobre TODAS las rutas).

Una vez hecho esto solo tendríamos que agregar un unico permiso, que sería este:

"CodeBase file:/C:/datos/*"

Con esto permitiríamos UNICAMENTE que se puedan leer ficheros en la carpeta indicada.

![Captura 9 Solo leer en c datos](https://github.com/arkaland/PSP6/blob/master/imagenes/9_Exportar%20llave%20publica.JPG)

Con esto concluye este readme. 
