# Umbral Dual Blades — Forge 1.20.1 / Epic Fight 20.14.17

Development package targeting:

- Minecraft 1.20.1
- Forge 47.4.20
- Epic Fight 20.14.17
- Java 17

## Implemented in v0.1.0

- One `shadow_blade` item; the recipe creates two copies for dual wielding.
- 12 base attack damage before enchantments.
- Epic Fight sword capability with 20% Armor Negation.
- Epic Fight's standard sword dual-wield style as a temporary animation layer.
- Seven-successful-impact counter; hit seven receives a configurable 1.30x multiplier.
- Back attacks receive a configurable 1.20x multiplier.
- Restrained shadow particles and pitch-varied hit sounds.
- No custom parry implementation.
- Guard/parry ownership remains with Epic Fight and installed addons such as Dodge Parry Reward.

## Important scope note

This is the stable first development layer, not the finished 25-animation combat system. The six heavy branches, four original aerial attacks, drill flight, cinematic counter and original Blender animations are deliberately not represented by fake placeholders.

## Build

Option A: install a 64-bit JDK 17 and Gradle 8, then run from this folder:

```bat
gradle build
```

The output should be:

```text
build/libs/umbral_dualblades-0.1.0.jar
```

Option B: upload the project to GitHub and follow `BUILD_WITH_GITHUB.md`; the included workflow compiles and uploads the reobfuscated JAR automatically.

Copy the resulting JAR into a test instance containing Forge 47.4.20 and Epic Fight 20.14.17. Test with the minimal set first, then add the combat addons, and only afterward test the full Epicity instance.

## In game

Use the Combat creative tab or:

```mcfunction
/give @s umbral_dualblades:shadow_blade 2
```

Equip one blade in each hand while in Epic Fight battle mode.
