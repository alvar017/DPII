Las pruebas se han realizado para medir el rendimiento con respecto al 
requisito 23.1

================================================================================
Informaci�n General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500ms y desviaci�n de 100 ms 
en el "login" y en "list"("Gaussian Random Timer").
Todas las pruebas se han realizado con constante de 3000ms y desviaci�n de 1000 ms 
en el "create"("Gaussian Random Timer").
================================================================================

================================================================================
Prestaciones:

Procesador: Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz 2.20GHz.
Memoria instalada (RAM): 8.00 GB.
Sistema Operativo de 64b, procesador x64.

**USANDO MAQUINA VIRTUAL APORTADA PARA LA ASIGNATURA**
================================================================================

================================================================================
--PRUEBAS--
================================================================================
Prueba 1: 

#usuariosConcurrentes = 50;
   - Error%: aparece una peque�a cantidad de errores para este n� de usuarios.
   - 90%Line: no aparecen "delays" destacables.
================================================================================
Prueba 2: 

#usuariosConcurrentes = 100;
   - Error%: aparecen una peque�a cantidad de errores.
   - 90%Line: Aunque elevados, los "delays" a�n son aceptables (�2000ms).
================================================================================
Prueba 3: 

#usuariosConcurrentes = 120;
   - Error%: no hay errores para este n�mero de usuarios.
   - 90%Line: Altos. Los "delays" se encuentran ligeramente por encima del umbral
     valido (est�n por encima de 2500ms).
================================================================================
--CONCLUSI�N--
================================================================================
El n� de usuarios concurrentes v�lido recomendado es unos 110 usuarios seg�n las
pruebas realizadas. 
================================================================================