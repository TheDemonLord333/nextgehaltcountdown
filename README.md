# Nächstes Gehalt – Countdown App

Eine Android-App, die einen Live-Countdown bis zum nächsten Gehaltstermin anzeigt.

## Funktionsweise

- **Zahltag** = 7. Werktag (Montag–Freitag) jedes Monats
- **Großer Countdown** mit Tagen, Stunden, Minuten und Sekunden
- **Datum** des nächsten Gehalts wird angezeigt
- **Liste** der nächsten 6 darauf folgenden Gehaltstermine

## Screenshots

```
┌────────────────────────────────┐
│        GEHALTSRECHNER          │
│     Nächstes Gehalt in         │
│     Dienstag, 11. März 2025    │
│                                │
│  ┌──────────────────────────┐  │
│  │  08  :  14  :  33  :  07 │  │
│  │ TAGE   STD    MIN    SEK  │  │
│  └──────────────────────────┘  │
│                                │
│  WEITERE GEHALTSTERMINE        │
│  ┌──────────────────────────┐  │
│  │ ① 10. April 2025  Do 💶  │  │
│  │ ② 09. Mai 2025    Fr 💶  │  │
│  │ ...                      │  │
│  └──────────────────────────┘  │
└────────────────────────────────┘
```

## Projekt öffnen

1. Android Studio öffnen
2. `File → Open` → dieses Verzeichnis wählen
3. Gradle sync abwarten
4. App auf Gerät oder Emulator starten (minSdk 26 / Android 8.0)

## Technologie

- **Java** (kein Kotlin, kein Compose)
- `java.time` API für Datumskalkulation
- `RecyclerView` für die Terminliste
- `Handler` + `Runnable` für den Sekundentakt
- Material Components (Dark Theme)
