Para la realizaci�n de este test ha sido necesario generar los datos a borrar porque tenemos que tener controlado su rango de ID para usar un contador en JMeter y borrar de ese modo una entidad cada vez. Para generar los datos de prueba se ha realiza un script en python que crea un archivo txt con los beans. En este caso se ha ejecutado en consola:
> python3 position_bean_generator.py 8750
8750 es el n�mero de beans a crear.

Con 150 usuarios muestra un rendimiento adecuado. Un 90% line m�ximo de 1831ms.

Con 175 usuarios y 50 loops abortamos el test ya que el porcentaje de error comenz� a aumentar fren�ticamente.

Prestaciones:
 == Ordenador F�sico ==
 - Procesador: Intel Core i7-4720HQ CPU @ 2.60GHz � 8 
 - RAM: 16GB
 - Disco Duro: TOSHIBA-TR200 SSD (240GB)
 == M�quina virtual ==
 - CPUs: 4
 - RAM: 4GB
