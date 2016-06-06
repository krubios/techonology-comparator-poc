## Techonology-comparator-poc

Esta herramienta está desarrollada en lenguaje de programación JAVA, por lo que es necesario tener instalado en el equipo una máquina virtual de JAVA o JVM. Durante el desarrollo y pruebas de la herramienta se ha empleado la versión 7 de Oracle. Después de instalar la JVM se podrá iniciar la aplicación.

La aplicación de escritorio creada se basa en una aplicación Eclipse RCP (Rich Client Plattform).
Una aplicación Eclipse necesita ser exportada para ejecutarse fuera del entorno de desarrollo. Para ello se necesita un fichero de configuración del producto. Dicha exportación crea una carpeta con todos los artefactos necesarios para poder ejecutar la aplicación.

Se ha generado un lanzador específico para las siguientes plataformas:
- Windows: abriendo la carpeta “win32.win32.x86” nos encontramos con la carpeta que contiene la aplicación “TechnologyCompare”. Haciendo click en la aplicación “TechnologyCompare.exe” se ejecuta la aplicación en un sistema operativo de 32 bits.

   Si la JVM instalada es para un sistema operativo de 64 bits, habrá que hacer el mismo procedimiento pero seleccionando la carpeta win32.win32.x86_64.

- Linux: el procedimiento en este caso es el mismo que en el apartado de Windows. La única diferencia es que la carpeta inicial que hay que seleccionar para un sistema de 32 bits es “linux.gtk.x86”, mientras que para un sistema operativo de 64bits la carpeta que hay que seleccionar es “linux.gtk.x86_64”.

La aplicación se controla mediante una interfaz gráfica que nos proporciona una aplicación eclipse RCP. En los siguientes apartados se comentarán las vistas de información y las vistas de configuración para comparar las tres tecnologías inalámbricas WiFi, WiMAX y Sistemas TDMA propietarios de WiFi al que nos refriremos como WiFi TDMA.

### Pantalla principal

En la sección de la izquierda de esta vista se mostrará una lista de estaciones subscriptoras definidas en el fichero “fileName.report” exportado de la herramienta Radio Mobile. 

En la sección de la derecha, se mostrará la información sobre los enlaces seleccionados, es decir, información entre la estación base y la estación subscriptora que aparece en la sección de la izquierda. La información que se muestra se divide en 2 secciones, una relativa a la información de Radio Mobile y otra relativa  a la tecnología, modulación y capacidad ofrecida.

La herramienta interpreta ficheros report exportados de la aplicación Radio Mobile en inglés y español.

Por otro lado, si el fichero report no tiene toda la  información disponible para cargar las redes, mostrará los siguientes mensajes de error:

- Error al cargar las redes

  Se produce cuando no tenemos toda la información necesaria, como la localización de todas las estaciones.

-	Error con el nombre de los equipos que no están bien definidos.

  La herramienta acepta un máximo de 18 caracteres como nombre de los equipos.

-	Error al leer los usuarios de radiomobile

  Se produce cuando existe algún error en los parámetros de los equipos.
  
### Menú de archivo

En el menú de Archivo se ofrecen las siguientes funciones:

- Abrir archivo: Para abrir un fichero exportado de Radio Mobile se puede hacer desde la ruta Archivo - Abrir Archivo o bien   seleccionando el icono de la carpeta que se encuentra justo debajo del panel de herramientas. Además de seleccionar el fichero se solicitará que se seleccione una de las redes definidas en dicho fichero.

- Open in new Window: Esta opción permite abrir otra ventana de la pantalla principal, pudiendo así trabajar en varias ventanas simultáneamente.

- Exit: esta opción permite cerrar la aplicación.


### Menú de configuración

En el menú de configuración tenemos acceso al submenú de “Preferences”, que permite configurar los siguientes parámetros:

Capacidad WiMAX
- Prefíjo Cíclico: necesario para combatir el multitrayecto. Cuanto mayor sea, mejor se comportará frente al multitrayecto, pero al     aumentar su valor se reduce el throughput máximo que ofrece el canal.
- Ancho de Banda: se encuentra en megahercios. Un canal más estrecho reduce el throughput pero mejora el comportamiento frente a ruido   e interferencias.
- Tiempo de trama: se encuentra en milisegundos. Con un canal de trama más alto el canal puede ofrecer un throughput superior, pero a   costa de aumentar la latencia de los flujos.
- Porcentaje de trama usado para el DL: es el porcentaje de tiempo de trama que se dedicará a la subtrama del downlink. En este campo   se puede introducir valores entre 1% y el 99%. En este caso está configurado por defecto al 51%.
- Porcentaje de trama usado para el UL: es el porcentaje de tiempo de trama que se dedicará a la subtrama del uplink. También se puede   introducir valores entre el 1% y el 99%. En este caso está configurado por defecto al 49%. 
  Si se modifica alguno de los dos porcentajes el otro se verá modificado hasta completar la suma del 100%.

Capacidad WiFi

- SIFS/RIFS: se trata del tiempo de separación entre tramas. RIFS se utiliza cuando no hay un tiempo SIFS. El valor de RIFS es de 2µs   en la capa PHY de 802.11n. 
- Número de paquetes: se trata del número de paquetes a enviar. Por defecto 42.
- Tamaño de paquetes: representa el tamaño de paquetes en bits. Por defecto 12000.
- Block ACK: inicialmente tiene como finalidad mejorar la eficiencia de la capa MAC. Es decir, en lugar de transmitir un ACK por cada   MPDU, múltiples MPDU se pueden reconocer por un único block ACK. Por defecto, esta opción está activada.
- Porcentaje de trama usado para el DL: porcentaje de tiempo de trama que se dedicará al downlink. Se puede introducir valores entre    1% y el 99%. Está configurado por defecto al 50% (0.5)
- Porcentaje de trama usado para el UL: porcentaje de tiempo de trama que se dedicará al uplink. Se puede introducir valores entre el   1% y el 99%. Está configurado por defecto al 50%(0.5).

Al igual que en WiMAX, si se modifica el porcentaje de trama en un sentido, automáticamente se modificará el otro sentido hasta sumar el 100% entre los dos sentidos.

Capacidad WiFi-TDMA

- Porcentaje de trama usado para el DL: porcentaje de tiempo de trama que se dedicará al downlink. Se puede introducir valores entre    1% y el 99%. Está configurado por defecto al 50% (0.5)
- Porcentaje de trama usado para el UL: porcentaje de tiempo de trama que se dedicará al uplink. Se puede introducir valores entre el   1% y el 99%. Está configurado por defecto al 50%(0.5).

Al igual que en WiMAX y WiFi, si se modifica el porcentaje de trama en un sentido, automáticamente se modificará el otro sentido hasta sumar el 100% entre los dos sentidos.

Sistema WiMAX

- Modulación: representan las modulaciones disponibles en equipos WiMAX.
- Potencia transmitida: representa a la potencia transmitida en decibelio-milivatio de un equipo WiMAX de acuerdo a la modulación       seleccionada.
- Sensibilidad: representa la sensibilidad en decibelio-milivatio de un equipo WiMAX según la modulación empleada.

Los valores por defecto se han tomado de los sistemas de ARBA series por Albentia Systems.

Sistemas WiFi

- MIMO: se trata de un parámetro de configuración para indicar si es un sistema MIMO o no. Por defecto a false. 
- Modulación: posibles modulaciones de un equipo WiFi. Por defecto acepta modulaciones hasta MCS7, en caso de activar la opción de      MIMO se incluyen las modulaciones desde MCS8 a MCS15.
- Potencia transmitida: potencia transmitida en decibelio-milivatio de un equipo WiFi según la modulación utilizada.
- Sensibilidad:se trata de la sensibilidad de un equipo WiFi en decibelio-milivatio de acuerdo a la modulación seleccionada.
- Capacidad RTS: se permite la opción de activar el modo RTS. Por defecto la herramienta trabaja en modo CTS.

### Menú de ayuda

En este menú se muestra una información resumida de la aplicación.











