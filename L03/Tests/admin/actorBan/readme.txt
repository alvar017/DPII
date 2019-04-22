Las pruebas se han realizado para medir el rendimiento con respecto al 
requisito 24.2

================================================================================
Informaci�n General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500 y desviaci�n de 100 ms 
en el "login", en el "actorList" y en "banActor"("Gaussian Random Timer").
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
   - 90%Line: aunque altos, tanto el "delay" en "login"(1863ms), como en 
     "actorList"(1837ms)siguen siendo aceptables (<2000ms). Sin embargo, el 
     "banActor"(2179ms) supera por poco el umbral v�lido.
================================================================================
Prueba 3: 

#usuariosConcurrentes = 125;
   - Error%: aparecen una peque�a cantidad de errores.
   - 90%Line: Altos. El "delay" en "login"(2591ms), el "delay en "actorList"
     (2465ms) y el "delay" en "banActor"(2294ms) son excesivos.
================================================================================
--CONCLUSI�N--
================================================================================
El n� de usuarios concurrentes v�lido recomendado es unos 110 usuarios seg�n las
prubas realizadas. 
================================================================================