Establecemos como l�mite los 190 usuario ya que comprendemos que una acci�n de iniciar sesi�n no debe tomar m�s
de 3 segundos, aunque el resto de percentiles a�n se mantienen estables entre 1'8 y 1'6 segundos.

Los par�metros de esta prueba han sido 250 usuarios con 20 bucles.

La CPU ha sido la causante de nuestro cuello de botella, por mantenerse al 100% todo el tiempo a pesar de que
otros componentes se mantienen al m�nimo nivel.

Prestaciones:

HP ay150ns, intel i7 7500U con SSD Samsung EVO 860 como memoria principal y 16 GB de memoria RAM.

No se usa la m�quina virtual, JMETER y Tomcat directo sobre Windows 10.