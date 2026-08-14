# Medusa Fest 2026 · guia pessoal

Aplicativo Android não oficial para acompanhar o Medusa Festival em Cullera. Esta versão foi preparada para **sexta-feira, 14 de agosto**, e **domingo, 16 de agosto de 2026**, com a programação dos nove palcos:

- Apsaras
- Resonance
- Arcade Land
- Beyond
- Dharma
- Beach Club
- Vertigo
- Church Club
- Poliakow Club

## Recursos

- artista tocando agora e próximo set em cada palco;
- programação completa por palco e dia;
- guia de gêneros, clima, intensidade e diferenças entre sexta e domingo;
- perfil pesquisado dos 118 artistas, com gênero, assinatura e referências relevantes;
- Radar Trance que diferencia trance confirmado de artistas apenas adjacentes ao gênero;
- favoritos com alerta no início do set;
- busca de artistas no Spotify, YouTube, SoundCloud e Google;
- widget com Apsaras, Resonance e Arcade Land;
- nove artes exclusivas — uma identidade visual própria para cada palco.

## Fontes da programação

Os horários foram transcritos dos materiais publicados pelo Medusa Festival:

- [Horários oficiais](https://www.medusasunbeach.com/horarios)
- [Publicação oficial sobre os nove palcos](https://www.instagram.com/p/Db8qCf9CBPe/)

O festival pode alterar a grade. Confirme os horários nos canais oficiais antes do evento.

As descrições artísticas foram cruzadas com páginas oficiais, selos, agências, imprensa musical e publicações especializadas. Quando não há biografia pública confiável, o texto se limita ao papel do artista na curadoria do palco, sem atribuir faixas ou histórico não verificados.

## Compilar

Requisitos: JDK 17 e Android SDK 34.

```bash
./gradlew assembleDebug
```

O APK será criado em `app/build/outputs/apk/debug/app-debug.apk`. O workflow **Build Medusa APK** também compila e disponibiliza o APK como artefato no GitHub Actions.
