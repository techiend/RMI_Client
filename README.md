# RMI_Client
# REQUERIMIENTOS

1. JAVA 16 
2. Maven 3.6.3 

# INSTRUCCIONES

## Compilación
Una vez descargado el código fuente, ubicarse en la raiz del proyecto y ejecutar el comando:

    mvn clean package

Esto compilará el proyecto y creara el archivo .jar a ejecutar dentro del directorio:

`target/RMI_Client-{$version}.jar`

## Ejecución
Ubicarse en el directorio: `target/`

Una vez ubicado en el directorio puede ejecutar el proyecto de la siguiente forma:

    java -jar RMI_Client-{$version}.jar

## NOTAS
1. En caso de realizar pruebas en un IDE la variable "test" ubicada en la clase "Cliente" debe ser cambiada a true.
2. Al tener la variable "test" en false, la clave del usuario no se mostrara por pantalla (parecera que nada se escribe.)