Las pruebas se han realizado para medir el rendimiento con respecto al 
requisito 24.1

================================================================================
Informaci�n General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500 y desviaci�n de 100 ms 
en el "login" y en el "actorList" ("Gaussian Random Timer").
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
   - Error%: no aparecen errores para este n� de usuarios.
   - 90%Line: no aparecen "delays" destacables.
================================================================================
Prueba 2: 

#usuariosConcurrentes = 100;
   - Error%: aparecen una peque�a cantidad de errores.
   - 90%Line: aunque altos, tanto el "delay" en "login"(2046ms) y en "actorList"
     (2395ms) siguen siendo aceptables (�2000ms).
================================================================================
Prueba 4: 

#usuariosConcurrentes = 130;
   - Error%: aparecen una gran cantidad de errores.
   - 90%Line: Altos. Tanto el "delay" en "login"(2828ms), como el "delay en 
     "actorList"(2527ms) son inaceptables.
================================================================================
--CONCLUSI�N--
================================================================================
El n� de usuarios concurrentes v�lido recomendado es unos 115 usuarios seg�n las
prubas realizadas. 
================================================================================