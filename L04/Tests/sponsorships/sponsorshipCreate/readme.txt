Las pruebas se han realizado para medir el rendimiento con respecto al 
requisito 13

================================================================================
Informaci�n General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500 y desviaci�n de 100 ms 
en el "login" y en el "sponsorshipCreate"("Gaussian Random Timer").
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

#usuariosConcurrentes = 150;
   - Error%: no aparecen errores para este n� de usuarios.
   - 90%Line: Muy altos, pero a�n aceptables. Algo menos de los 3000ms. List
================================================================================
Prueba 2: 

#usuariosConcurrentes = 130;
   - Error%: no aparecen errores para este n� de usuarios.
   - 90%Line: Tiempos altos, pero aceptables.ee
================================================================================
Prueba 3: 

#usuariosConcurrentes = 115;
   - Error%: no aparecen errores para este n� de usuarios.
   - 90%Line: Delays aceptables.
================================================================================
--CONCLUSI�N--
================================================================================
El n� de usuarios concurrentes v�lido recomendado es unos 130 usuarios seg�n las
pruebas realizadas. 
================================================================================