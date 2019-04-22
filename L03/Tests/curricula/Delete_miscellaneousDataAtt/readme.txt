Entendemos como 264 usuarios, 32 bucles y 8448 registro a borrar el l�mite de nuestra aplicaci�n.

A partir de esta configuraci�n el login se eleva a casi dos segundos y medios, algo no usable para el d�a a
d�a de los usuarios.

El procedimiento ha sido crear un n�mero de registros (hilos * iteraciones) usando el programa java 
"generateCurriculaMiscellaneousDataPopulate.zip" en el que le indicamos el n�mero de entidades que queremos. Insertamos
estas en el populate,  y lo ejecutamos.

Posteriormente creamos un contador en Jmeter cuyo n�mero de inicio sea la id de la primera entidad que
se ha generado en mySql, donde su incremento sea de 1 y su nombre de variable counter_number por ejemplo. 
Buscamos el m�todo get de delete e introducimos el nombre del contador (counter_number) como variable. As�
por cada usuario e iteracci�n el contador le indicar� el n�mero de id que tiene que borrar, el cual gracias a
jmeter ser� �nico por cada hilo e iteracci�n, siendo as� un caso totalmente real de prueba donde podemos 
dividir el caso de crear y de borrar.

Prestaciones:

HP ay150ns, intel i7 7500U con SSD Samsung EVO 860 como memoria principal y 16 GB de memoria RAM.

No se usa la m�quina virtual, JMETER y Tomcat directo sobre Windows 10.