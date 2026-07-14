# Obtener el JAR sin instalar Gradle

1. Crea un repositorio vacío en GitHub.
2. Sube **todo el contenido** de esta carpeta, incluida `.github`.
3. Abre la pestaña **Actions**.
4. Selecciona **Build Umbral Dual Blades**.
5. Pulsa **Run workflow**.
6. Cuando termine, abre la ejecución y descarga el artefacto `umbral-dualblades-0.1.0-forge-1.20.1`.
7. Dentro estará el JAR compilado y reobfuscado para Forge.

Antes de usarlo en Epicity, crea una instancia de prueba con:

- Minecraft 1.20.1
- Forge 47.4.20
- Epic Fight 20.14.17
- Umbral Dual Blades
- Dodge Parry Reward 20.14.3 (después de comprobar el arranque básico)

El mod no registra un parry propio. La guardia/parry queda a cargo de Epic Fight y Dodge Parry Reward.
