Primero hemos realizado pruebas peque�as con 100 usuarios y 1 loop, con 100 usuarios y 10 loop para afianzar bien
como utilizar los csv en las pruebas de registro. Finalmente, realizamos una prueba de 100 usuarios y 50 loops donde
obtenemos un percentil de 1.6 segundos para ralizar el registro de un hacker. Intentamos realizar pruebas con un mayor
n�mero de usuarios (150 usuarios y 50 loops) pero el percentil obten�a un valor entorno a los 2.5 segundos.

En conclusi�n, establecemos que el n�mero m�ximo de usuarios que se pueden registrar en nuetra aplicaci�n es de 5000
usuarios.

Para realizar estas pruebas hemos tenido que hacer uso de un csv, ya que, el nombre de usuario y el email de un
actor deben ser �nico. Para general el csv hemos utilizado un peque�o programa que se encuentar en la carpeta 
generateUserNameEmail y los diferentes archivos generados para hacer las pruebas se encuentran en archivos generados.

Prestaciones:

MSI GS73 Stealth 8RG, intel i7-8750H 

Usando m�quina virtual.