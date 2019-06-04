Entendemos como 95 usuarios, 20 bucles y 1900 curriculas a borrar el l�mite de nuestra aplicaci�n.

Con esta configuraci�n a pesar de que la aplicaci�n no muestra porcentaje de error el percentil alcanza
valores muy altos, ve�se en las capturas los valores del login del usuario.

La CPU ha sido la causante de nuestro cuello de botella, v�ase las capturas de rendimiento.

El procedimiento ha sido crear un n�mero de curriculas (hilos * iteraciones) usando el programa java 
"generate_data.zip" en el que le indicamos el n�mero de usuarios y el n�mero de
bucles que queremos que queremos. Las curriculas ser�n creadas y deberemos 
a�adirlas a nuestro PopulateDatabase.xml (se cede uno a modo de prueba).

Posteriormente creamos un contador en Jmeter cuyo n�mero de inicio sea la id de la primera auditoria que
se ha generado en mySql, donde su incremento sea de 1 y su nombre de variable counter_number por ejemplo. 
Buscamos el m�todo get de delete e introducimos el nombre del contador (counter_number) como variable. As�
por cada usuario e iteracci�n el contador le indicar� el n�mero de id que tiene que borrar, el cual gracias a
jmeter ser� �nico por cada hilo e iteracci�n, siendo as� un caso totalmente real de prueba donde podemos 
dividir el caso de crear y de borrar.

Prestaciones:

HP ay150ns, intel i7 7500U con SSD Samsung EVO 860 como memoria principal y 16 GB de memoria RAM.

No se usa la m�quina virtual, JMETER y Tomcat directo sobre Windows 10.