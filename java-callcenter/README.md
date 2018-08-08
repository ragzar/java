
# CONSIGNA


Existe un call center donde hay 3 tipos de empleados: operador, supervisor
y director. El proceso de la atención de una llamada telefónica en primera
instancia debe ser atendida por un operador, si no hay ninguno libre debe
ser atendida por un supervisor, y de no haber tampoco supervisores libres
debe ser atendida por un director.


# SOLUCION

* El manejo de llamadas se realiza por una clase Singleton (Dispatcher), recibe un objeto (Caller) el cual simula el ingreso de una llamada y pueden ser recibidas de forma concurrente.

* La llamada ingresa directamente a una cola de espera la cual es monitoreada constantemente por el objeto (Processor).

* Hay una base de 10 empleados creados por defecto (5 Operadores, 3 Supervisores y 2 Directores)

* Se pueden ingresar empleados a la lista de receptores, estos son ordenados por prioridad de atención

* La atención de las llamadas se realiza simulando el patrón Observer con un modificación en la cual solo el primer observador disponible recibirá el objeto 

* Cada empleado u observador modifica su estado al iniciar la atención de una llamada y nuevamente al finalizar

* Las llamadas duran entre 5 a 10 segundos de forma aleatoria.

* Existe una prueba unitaria de 10 hilos concurrentes y por cada hilo se simula 5 llamadas. esta se valida con la comparación de las llamadas procesadas.


# EXTRAS

* Cuando no hay empleados disponibles, las llamadas se siguen registrando en una cola de espera hasta que el primer empleado disponible reciba las llamadas. así hasta que la cola quede sin llamadas.

* Existe un pool de hilos con tamaño fijado a 10. si hay más de 10 llamadas concurrentes estas quedan en espera que haya un hilo disponible, el pool máximo es configurable.

* Existe una clase con un método main para probar la funcionalidad de hilos y llamados concurrentes junto a nuevos empleados.
