Entendemos como 450 usuarios y 5 bucles el l�mite de nuestra aplicaci�n para el caso de uso conjunto de
crear y editar.

La causa de parar con esta configuraci�n de usuarios y bucles es debido a que la acci�n de iniciar sesi�n
alcance valores cercanos a los 3 segundos.

**************************************************************************************************************
INSTRUCCIONES DE USO

Antes de comenzar a leer es recomendable ver la captura de v�deo con el nombre "example_video.mp4"

Para la realizaci�n de este test hemos obtado por el uso conjunto de un contador y un archivo .CSV

Ha sido necesaria la utilizaci�n de esta metodolog�a debido a que una "Audit" requiere de una "Position" para
ser creada, sin embargo, por cada "Position" cada usuario solo puede crear una entidad "Audit". En nuestra
base de datos populada solo contamos con 5 "Position" viables para la creaci�n de una "Audit" y como su labor
de creaci�n es muy engorrosa hemos decidido crear un usuario por cada hilo que utilicemos (con la ayuda del 
archivo CSV) y por cada uno de estos usuarios ser�n creadas 5 "Audits" cada una de ellas referenciando a una
"Position" diferente siendo aqu� el elementos contador necesario para la diferenciaci�n de cada una de ellas.

En primer lugar tendremos que usar dos archivos utilizando los peque�os m�todos Java incluidos en el
paquete "generate_data.zip" el cual incluye dos clases, una para generar un a�adido para el 
PopulateDataBase.xml, en esta primera clase le indicaremos el n�mero de usuarios que queremos y nos
generar� por consola los bean necesarios que tendremos que inyectar a nuestro Populate (userAccount, 
Auditor y CreditCard). La segunda clase la utilizaremos para el archivo CSV, le indicaremos el n�mero
de usuarios (debe ser coherente con lo anterior) y el n�mero de repeticiones. Crearemos un archivo .txt
en el cual pegaremos lo generado por consola con este m�todo.Posteriormente pegamos el archivo en la ruta
corrrecta para interactuar con jMeter y posteriormente le cambiamos la extensi�n a .csv

Para una mejor comprensi�n de lo expresado anteriormente se recomienda una vez m�s la visi�n del v�deo 
"example_video.mp4"
**************************************************************************************************************


Prestaciones:

HP ay150ns, intel i7 7500U con SSD Samsung EVO 860 como memoria principal y 16 GB de memoria RAM.

No se usa la m�quina virtual, JMETER y Tomcat directo sobre Windows 10.