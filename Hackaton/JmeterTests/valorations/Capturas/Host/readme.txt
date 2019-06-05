Las pruebas se han realizado para medir el rendimiento con respecto a las valora
ciones de un host. Solo se prueba hacia un tipo de actor porque el rendimiento 
debe ser el mismo.
================================================================================
Informaci�n General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500 y desviaci�n de 100 ms.
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

#usuariosConcurrentes = 65;
   - 90%Line: Bajos. Admite m�s usuarios.
================================================================================
Prueba 2: 

#usuariosConcurrentes = 80;
   - 90%Line: Tiempos medios (1500ms). Admite m�s usuarios.
================================================================================
Prueba 3: 

#usuariosConcurrentes = 100;
   - 90%Line: Delays muy bajos. Admite mas usuarios.
	** En este punto se ejecuto de nuevo "PopulateDataBase.java" a causa de
	la falta de memoria de la MV. Por ello, el rendimiento se incremento 
	exponencialmente. Adem�s, aunque podr�a aumentar m�s el n�mero de hilos
	de la prueba, el m�x. de usuarios concurrentes ya esta limitado a mucho
	menos de los que esta prueba podr�a admitir.
================================================================================
--CONCLUSI�N--
================================================================================
El n� de usuarios concurrentes podr�a ser muy superior a otras partes de la app.
================================================================================