# IA API Rest

Proyecto demostración para utilizar agentes IA de codificación.

# Arrancar un proyecto

Este proyecto utiliza OpenCode:

https://opencode.ai/

Una vez tengas instalado opencode viene por defecto con modelos de uso gratuito (limitado)

## Conceptos a entender para usar desarrollo con IA

- **Modelo**: Un LLM es un modelo predictivo que por probabilidad "adivina" cuál es el texto más adecuado para continuar una conversación.
- **Contexto**: La cantidad de información limitada que puede introducirse en la conversación para evaluarse al dar una respuesta. Incluye también la información del system prompt, propósito general del programa.
- **Alucionaciones**: Predicciones no esperadas, normalmente derivado de un exceso de información poco relevante en la ventana de contexto o de falta de capacidad del modelo.
- **Agente**: Un programa que tiene acceso a herramientas en tu equipo y puede llegar a ejecutar acciones, más allá de darte intrucciones como un chat, dirigido por un system prompt.

### Problemas actuales de la IA

Los principales problemas que tiene la IA en el estado actual son:

*Limitación del contexto y capacidad del modelo.*

- Tenemos modelos con limitación en el tamaño de contexto que pueden manejar.
- Aunque están apareciendo modelos con ventanas de contexto superiores al millón de tokens, menos es más.
- Paradójicamente cuanta más información incluye el contexto más fácil es que haya datos irrelevantes para solventar el problema y más fácil es que la solución no se ajuste a lo que esperas.

*Necesidad de cómputo y consumo*

- Para tener respuestás ajustadas y rápidas para las tareas que requerimos en desarrollo se necesitan modelos grandes.
- Los modelos de ejecución local aunque "funcionen" son mucho más lentos y menos precisos, no compensan.
- La solución aquí es simple, conseguir acceso a un modelo en nube suficientemente potente como para hacer el trabajo de forma correcta.
- Esto introduce otro problema derivado, el modelo de negocio es pago por token por lo que conviene "optimizar" el uso de tokens de estos en las peticiones a estos modelos.

*Modelos diferentes para tareas diferentes*

- Hay modelos más adecuados para programar, otros más adecuados para planificar, dibujar, resumir, esquematizar,...
- Podemos utilizar un único modelo para todo, pero es otro punto optimizable.

### Soluciones actuales

Básicamente tenemos que solventar dos problemas, optimizar la ventana de contexto y el uso de tokens. 

El uso de tokens puede depender mucho del agente que utilices y del modelo, hay modelos más "gastones" que otros o agentes (system prompts) más verbosos.

Por lo tanto para este primer problema es más cuestión de prueba y error y consultar las estadísticas que puedas recopilar.

Otro problema es el uso de diferentes modelos. Afortunadamente los agentes permite utilizar muchos modelos y puedes crear agentes diferentes para Planear, Construir, Documentar,...

Aquí ocurre igual tendrás que depender de la prueba y error para lograr encontrar aquello que te guste y por suerte los servicios de pago suelen incluir diferentes modelos incluidos (aunque a diferentes costes).

Queda un único problema:

***¿Cómo optimizo la ventana de contexto para que tenga toda la información necesaria para solventar mi siguiente petición?***


# AGENTS.md

El objetivo es disponer de un documento de texto que describa el proyecto de forma adecuada para indicar al agente todas las características que consideres importantes para tener en consideración y mejorar los resultados.

El agente incorpora SIEMPRE al contexto la información de este archivo, lo que te ahorra tener que repetirte en cada prompt.

https://agents.md/

Puedo incluir desde el propósito general del proyecto hasta el stack tecnológico, mis reglas de sintaxis, la forma en la que estructurar los archivos, etc...

Como recomendación general incluye:

- **Project overview**: Objetivo y propósito general del proyecto, pila tecnológica, metodología de trabajo.
- **Build and test commands**: Como se construye, se ejecuta o se lanzan los tests.
- **Code style guidelines**: Cuáles son las normas de estilo de código.
- **Testing instructions**: Instrucciones específicas para crear, ejecutar y verificar cambios.
- **Security considerations**: Consideraciones de seguridad y rendimiento.

### Fragmentar la información

Si tengo un proyecto muy grande es fácil que el fichero crezca de forma desmesurada y describa cosas que realmente no son necesarias para resolver la cuestión concreta que estamos pidiendo, pero que influyen (de forma impredecible y casi siempre negativa) en las respuestas que nos de el modelo.

Mantener un fichero por debajo de las 500 líneas de texto es lo mejor, piensa que siempre puedes tener ficheros AGENTS.md en cada módulo o carpeta para refinar las reglas a tener en cuenta en ese módulo en concreto y no sobrecargar las consideraciones generales del proyecto completo (a tener en cuenta en todo momento).

### Pedirle al agente que cree el fichero

Si usamos opencode tenemos un comando /init que analiza el contenido del proyecto y me genera un fichero que luego puedo "ajustar".

No hay mejor forma de decirle al agente cómo quieres que programe que con ejemplos, deja espacio para que el agente pueda añadir al contexto tu propio código o añade ejemplos en el fichero.

Aquí un ejemplo muy básico de fichero para este proyecto:

````
# AGENTS.md - IA Rest Project

API Rest para ....

## Pila tecnológica

- Spring Boot 4.0.2
- Spring Data JPA (HSQLDB in-memory)
- Spring Security OAuth2 Resource Server
- Spring HATEOAS
- Springdoc OpenAPI (Swagger UI)
- Micrometer + Prometheus (metrics)
- Cucumber 7.34.2 (BDD tests)
- JUnit 5 (unit tests)
- JaCoCo (code coverage)
- Java 17

## Restricciones

- Escribe siempre en español
- Nunca utilices comandos o instrucciones para eliminar ningún archivo del proyecto, solo sugiere el comando pero no lo ejecutes ni continues.

...

```` 

## Commands

Dentro de nuestro agente podemos crear comandos específicos que podemos invocar con `/comando`, esto será útil para invocar tareas repetitivas y reducir la necesidad de escribir prompts precisos.

https://opencode.ai/docs/es/commands/

Puedes crear tus propios comandos en la carpeta `.opencode/command` simplemente añadiendo un fichero en formato .md con las instrucciones del comando. Se pueden recibir e interactuar con los argumentos del comando de forma global $ARGUMENTS o individual $1, $2,...

```
---
description: Describe el propósito del comando
agent: <Puedes indicar si quieres restringir su uso a un agente concreto build/plan/...>
model: <Modelo a utilizar específico>
subtask: <false|true útil para ejecutar el comando en un contexto separado y no contaminar el general>
---

Describe el propósito del comando

**Instrucciones**

1. Primer paso del comando `ls -la $ARGUMENTS`
2. ....

```

## Skills

En ocasiones un comando no es lo suficientemente preciso y es mejor usar habilidades.

https://opencode.ai/docs/es/skills/


Puedes crear tus propias habilidades en la carpeta `.opencode/skills` simplemente añadiendo una carpeta con el nombre del comando y dentro un fichero SKILL.md con las instrucciones del comando.

Las habilidades pueden definir scripts en una carpeta además de ejemplos y referencias, en sus respectivas carpetas para poder estructurar y definir habilidades complejas.

```
---
name: Mi habilidad
description: Esta habilidad especial es útil para .....
---

**Instrucciones**

1. Primer paso del comando `.opencode/skills/mi-habilidad/scripts/script.sh`
2. ....

```

Existen repositorios de skills para ampliar las habilidades de tu agente de forma inimaginable:

https://skills.sh/

## Agentes

Utilizar diferentes agentes me permite controlar el contexto que conocen (sin reiniciar la herrmamienta) y los permisos que tiene para evitar incidentes no deseados.

OpenCode incluye dos tipos de agentes:

- Primarios: Responden de forma directa en el chat y puedes cambiar de uno a otro con tabulador.
- Subagentes: Se utilizan en un hilo sepadado con un contexto limpio.

Puedes crear tus propios agentes en la carpeta `.opencode/agents` como de costumbre con un .md

```
---
name: <nombre>
description: Describe la utilidad y las condiciones en las que el agente debe delegar tareas cuando no es un agente primario
mode: <primary/subagent>
model: <modelo a utilizar si deseas utilizar uno específico>
temperature: 0.1
tools: <https://opencode.ai/docs/es/tools/>
    question: false
    write: false
    edit: false
    bash: true
permission: <https://opencode.ai/docs/es/permissions/>
    skill:
        "*": deny
    task:
        "*": deny
        "explore": allow
    bash:
        "*": allow
---

<Describe el system prompt del agente>

```

Puedes dejar que el agente primario decida cuándi invocar a los subagentes o usar @nombre e invocar al agente directamente en el chat para una tarea, para los primarios puedes ir cambiando con tabulador.


## MCPs

A diferencia de las habilidades o los comandos en los que la definición de lo que hacen reside localmente los MCP es el mismo concepto pero en servidores de manera que posibilitan interactuar con elementos remotos normalmente usando APIs.

**ADVERTENCIA**: Ya puedes imaginar lo grave que es tener expuesto tu equipo local a un agente con permisos para hacer cosas no deterministas con ciertas herramientas. Imagina lo peligroso que puede llegar a ser descargar esas instrucciones de servidores no confiables y que acaben inyectando comandos en tu equipo. Procura instalar SOLO skills, comandos o MCPs oficiales.

Existe un repositorio oficial de MCPs: https://github.com/github/github-mcp-server

Vamos a instalar un MCP popular que es Context7 utilizado para acceder a documentación actualizada de pilas tecnológicas.

Debemos incluir la definición en el fichero opencode.json a nivel de proyecto o a nivel de usuario si queremos incluirlo en todos.

```
"mcp": {
    "context7": {
        "type": "remote",
        "url": "https://mcp.context7.com/mcp",
        "enabled": true
    },
    "excalidraw": {
        "type": "remote",
        "url": "https://mcp.excalidraw.com/mcp",
        "enabled": true
    }
}
```

