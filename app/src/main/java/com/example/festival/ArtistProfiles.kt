package com.example.festival

enum class TranceFocus(val label: String) {
    NONE(""),
    TRANCE("🌀 TRANCE EM FOCO"),
    ADJACENT("◌ RADAR TRANCE · ADJACENTE")
}

data class ArtistProfile(
    val tags: String,
    val description: String,
    val tranceFocus: TranceFocus = TranceFocus.NONE
)

private fun profile(
    tags: String,
    description: String,
    tranceFocus: TranceFocus = TranceFocus.NONE
) = ArtistProfile(tags, description, tranceFocus)

private val artistProfiles = mapOf(
    "Hektor Mass" to profile(
        "House energético · EDM · Barcelona",
        "DJ e produtor barcelonês de sets muito abertos e energéticos; passou por clubes como Amnesia Ibiza e Zouk Las Vegas e usa 'My Life' como um de seus cartões de visita."
    ),
    "Rello" to profile(
        "Hardstyle · talento espanhol",
        "Nome da nova geração espanhola escalado para aquecer o mainstage: espere kicks de hardstyle, melodias diretas e uma subida rápida de energia."
    ),
    "Wade" to profile(
        "Tech house · Criterio · Sevilha",
        "O sevilhano criou a label Criterio e virou referência do tech house espanhol com linhas de baixo elásticas, percussão latina e faixas como 'Por Ejemplo'."
    ),
    "Miss Monique" to profile(
        "Progressive house · melodic techno · Siona",
        "Ucraniana à frente da Siona Records, conhecida por construir viagens progressivas longas e melódicas; é uma ponte hipnótica para quem gosta da emoção do trance, embora não seja trance puro.",
        TranceFocus.ADJACENT
    ),
    "HALŌ" to profile(
        "Progressive house · supergrupo · estreia ao vivo",
        "Projeto conjunto de Matisse & Sadko, DubVision e Third Party: reúne três referências do progressive house e resgata a euforia melódica da era dourada dos festivais.",
        TranceFocus.ADJACENT
    ),
    "Dimitri Vegas" to profile(
        "Big room · neo-rave · hard dance",
        "Em formato solo, alterna hinos de big room com sua fase mais escura de neo-rave e hard dance; a releitura recente de 'Turn The Tide' ainda encosta no vocal trance.",
        TranceFocus.ADJACENT
    ),
    "DJs From Mars" to profile(
        "Mashup · EDM · electro house",
        "Dupla italiana dos capacetes de papelão, especialista em mashups que encaixam pop, rock, techno e EDM no mesmo drop; set de surpresa e reconhecimento instantâneo."
    ),
    "MANDY" to profile(
        "Hardstyle · rave · Dirty Workz",
        "A belga MANDY leva hardstyle acessível, vocais conhecidos e energia de rave; 'RaggaDrop' e o remix oficial de 'Jackie Chan', de Tiësto, mostram bem essa fórmula."
    ),
    "Energy Time" to profile(
        "Remember · dance clássica · Espanha",
        "Projeto voltado ao repertório remember espanhol: eurodance, melodias de club e clássicos de pista para fechar o mainstage em clima nostálgico."
    ),
    "Luxi Villar" to profile(
        "Hard groove · techno · UK rave",
        "DJ madrilenha ligada à plataforma On Going, combina hard groove, techno e referências UK; seus sets podem atravessar progressive tech house, rave e passagens de sabor trance."
    ),
    "Fatima Hajji" to profile(
        "Techno · hard techno · Silver M",
        "Veterana de Salamanca e fundadora da Silver M, imprime percussão tribal, raízes africanas e techno musculoso numa condução precisa e muito física."
    ),
    "Pawlowski" to profile(
        "Acid · neo-rave · techno 90s",
        "Francês de estética rave noventista, mistura acid, techno rápido e melodias bouncy/trance; 'Demonic Dimension' e o projeto Future Legacy resumem seu lado futurista e nostálgico.",
        TranceFocus.ADJACENT
    ),
    "Nico Moreno" to profile(
        "Hard techno · industrial · Insolent Rave",
        "Um dos nomes que popularizaram o hard techno francês: kicks enormes, clima industrial e pressão contínua; 'Purple Widow' é sua faixa mais emblemática."
    ),
    "Vendex" to profile(
        "Industrial techno · acid · Barcelona",
        "Produtor de Barcelona com conceito visual inspirado em Dante, alquimia e imaginário medieval; transforma isso em hard techno sombrio, industrial e ácido."
    ),
    "Fantasm" to profile(
        "Hard techno · industrial · 150–170 BPM",
        "Projeto francês de hard techno extremo, reconhecido por sets muito rápidos, distorção industrial e faixas como 'Venom'; é o fechamento mais agressivo da sexta no Resonance."
    ),
    "Papero" to profile(
        "Remember · hard dance · Espanha",
        "Veterano do circuito remember espanhol, abre o Arcade fazendo a transição entre dance clássica e os kicks mais duros que dominarão o palco."
    ),
    "Yeyo" to profile(
        "Hardstyle · hardcore · cena espanhola",
        "DJ recorrente da cena hard espanhola, escalado para aumentar o BPM cedo com uma seleção entre hardstyle e hardcore de festival."
    ),
    "Hnos. Kapiya" to profile(
        "Makina · remember · hard dance",
        "Dupla espanhola associada à memória da makina e do hard dance; melodias aceleradas e clima de rave clássica antes do rawstyle internacional."
    ),
    "Wakan B2B Dr. Evil" to profile(
        "B2B · hard dance · remember",
        "Encontro de dois nomes do circuito hard/remember espanhol: o formato B2B favorece trocas rápidas, clássicos inesperados e aumento constante de intensidade."
    ),
    "Bassdrum Project & Ogalla" to profile(
        "Hardstyle · hardcore · Espanha",
        "Parceria da cena hard nacional construída para o impacto do bass drum: um set de ponte entre a abertura espanhola e a sequência mundial de rawstyle."
    ),
    "Rebelion" to profile(
        "Rawstyle · Acid Reign · Escócia",
        "Dupla escocesa de rawstyle conhecida por kicks agressivos e produção cinematográfica; 'City Lights' e a série de shows Overdose mostram seus dois extremos."
    ),
    "Sound Rush" to profile(
        "Euphoric hardstyle · Art of Creation",
        "Irmãos gêmeos holandeses que equilibram kicks de hardstyle com melodias emotivas e refrões de festival; assinaram inclusive um hino da Defqon.1."
    ),
    "Phuture Noize" to profile(
        "Hardstyle conceitual · melodic raw",
        "Projeto de Marco Spronk, famoso por álbuns narrativos como 'Black Mirror Society' e 'Silver Bullet'; hardstyle para quem valoriza atmosfera e composição."
    ),
    "D-Sturb" to profile(
        "Rawstyle · kickrolls · melodic hardstyle",
        "Holandês reconhecido pelos kickrolls técnicos e por unir peso a melodias grandes; as séries 'Playground' e 'Through My Veins' definem sua identidade."
    ),
    "Wildstylez" to profile(
        "Hardstyle clássico · Project One",
        "Pioneiro do hardstyle melódico e metade do Project One com Headhunterz; é a chance de ouvir a escola que gerou hinos como 'Lose My Mind'."
    ),
    "Gunz For Hire" to profile(
        "Raw hardstyle · live act mascarado",
        "Live act de Ran-D e Adaro: personagens mascarados, narrativa de fora da lei e raw hardstyle teatral; o projeto completa 15 anos em 2026."
    ),
    "Partyraiser" to profile(
        "Hardcore · uptempo · Países Baixos",
        "Ativo desde os anos 1990, é peça central do hardcore holandês e costuma acelerar até o uptempo; 'Ode to the Godfather' traduz sua reputação."
    ),
    "Miguel Moore" to profile(
        "Remember · dance · abertura",
        "DJ espanhol responsável por abrir a viagem histórica do Beyond, preparando a pista com dance e clássicos club antes dos veteranos da noite."
    ),
    "Ismael Lora" to profile(
        "Hard house · dance · Rockola",
        "Criado musicalmente em Benidorm e residente da Rockola ainda jovem, consolidou-se no hard house e dance espanhol durante o auge dos anos 2000."
    ),
    "Coqui Selection" to profile(
        "House · tech house · Valencia",
        "Ativo desde 1991, passou por clubes valencianos como The Face, Woody e Apache; leva repertório extenso de house e tech house com memória de pista."
    ),
    "Chumi DJ" to profile(
        "Remember · sonido Límite · desde 1992",
        "Nome ligado ao histórico 'sonido Límite', começou em 1992 e representa a vertente mais melódica e reconhecível do remember levantino."
    ),
    "Javi Boss" to profile(
        "Hardcore · Central Rock · Bossland",
        "Valenciano com mais de três décadas de carreira e residência histórica no Central Rock; fundador da Bossland e presença habitual no Masters of Hardcore."
    ),
    "DJ Marta" to profile(
        "Remember · eurodance · trance clássico",
        "Apelidada de rainha do remember, revisita a era 1990–2002 de Radical com eurodance, italodance, hard house e clássicos de trance.",
        TranceFocus.ADJACENT
    ),
    "Rafa XL" to profile(
        "Remember · dance · Radical/Fabrik",
        "Nome do circuito remember de Madrid ligado a festas de Radical e Fabrik; aposta em refrões, melodias e dance de alta energia."
    ),
    "Raul Ortiz" to profile(
        "Remember · makina · dance-trance",
        "Residente da Fabrik desde 2004 e criador de La Resistencia, domina longas narrativas de remember, makina e dance-trance; aqui recebe ainda uma introdução própria.",
        TranceFocus.ADJACENT
    ),
    "Miguel Serna" to profile(
        "Remember · dance · lenda valenciana",
        "Três décadas de cabine e passagens por Rockola, Virtual e Masia fizeram dele uma referência do dance/remember valenciano dos anos 1990 e 2000."
    ),
    "Nuria Jump" to profile(
        "Jumpstyle · hardstyle · remember",
        "DJ espanhola ativa desde 2014, fecha o Beyond misturando jump, hardstyle, dance e remember com foco absoluto em energia."
    ),
    "BRESH" to profile(
        "Reggaeton · pop · latin hits",
        "Festa argentina que virou fenômeno global da Geração Z: cruza reggaeton, pop, hip-hop, nostalgia e hits virais numa ocupação de 12 horas, não num DJ set convencional."
    ),
    "DJ German" to profile(
        "House espanhol · Techno Flamenco",
        "Abre o takeover Techno Flamenco apresentando a proposta da noite: bases de club e referências espanholas antes da sessão central de Marsal Ventura."
    ),
    "Eloy GC B2B Erik Romero" to profile(
        "B2B · house/techno · Techno Flamenco",
        "B2B desenhado para o takeover: a troca entre Eloy GC e Erik Romero mantém o groove de house/techno com acentos ibéricos e clima de praia."
    ),
    "Ian Tules" to profile(
        "House · club espanhol · Techno Flamenco",
        "Terceiro passo da curadoria Techno Flamenco, aquece a pista com house percussivo e prepara a entrada da maratona de Marsal Ventura."
    ),
    "Marsal Ventura" to profile(
        "Techno Flamenco · dance · show de 4 horas",
        "Criador do conceito Techno Flamenco e do álbum 'De Berlín a Triana', funde eletrônica de festival e identidade espanhola numa sessão especial de quatro horas."
    ),
    "Los Prados" to profile(
        "House · sabor latino · Techno Flamenco",
        "Projeto inserido na curadoria Techno Flamenco para recuperar o groove após a sessão longa de Marsal, com uma leitura festiva e mediterrânea do house."
    ),
    "Dany BPM" to profile(
        "Hard dance · produtor · LOS40",
        "Produtor espanhol com mais de 25 anos e centenas de lançamentos, ligado também ao LOS40 Hard Dance; leva o takeover da dance ao hard dance."
    ),
    "Pomata" to profile(
        "Hard dance · remember · fechamento",
        "Fecha o Techno Flamenco no trecho mais acelerado, conectando a festa espanhola ao hard dance e ao remember das primeiras horas da manhã."
    ),
    "Mireia CJ" to profile(
        "House underground · warm-up · Valencia",
        "Abertura longa do clube Vertigo, com espaço para construir o house aos poucos e estabelecer a atmosfera íntima do palco."
    ),
    "Paula Fields" to profile(
        "House · tech house · cena espanhola",
        "DJ espanhola de circuito club e festival; ocupa o começo da noite com house de pista e uma progressão mais próxima da cabine do que do espetáculo de mainstage."
    ),
    "Karlos Molina" to profile(
        "Minimal/deep tech · house · Valencia",
        "Nome local da programação underground, trabalha o miolo da noite com groove minimal, basslines limpas e dinâmica de club."
    ),
    "Jaime Soeiro" to profile(
        "Minimal/deep tech · Techaway · Barraca",
        "DJ valenciano ligado à Barraca e à festa Techaway, especializado em minimal/deep tech de baixo marcado e condução hipnótica."
    ),
    "Fran Hernandez" to profile(
        "Tech house · ZIUR · Valencia",
        "Produtor valenciano e fundador da ZIUR, combina vocais, basslines profundas e groove de tech house numa sessão de duas horas."
    ),
    "Easttown" to profile(
        "House 90s · disco · Amsterdam",
        "Produtor de Amsterdam que recicla disco e house dos anos 1990 com acabamento moderno; tem lançamentos por selos como Hot Creations, Cécille e LTF."
    ),
    "DJs Comunidad Medusa" to profile(
        "Open decks · novos talentos · seleção surpresa",
        "Sessão coletiva dedicada à comunidade do festival: seis horas para descobrir DJs emergentes, com estilos e trocas de cabine que não são anunciados individualmente."
    ),
    "Asesor B2B Anderson R" to profile(
        "B2B de 3 horas · house · club local",
        "Abertura prolongada do Poliakow em formato B2B; três horas permitem construir do house mais leve ao groove de madrugada sem pressa."
    ),
    "Dario Huerta" to profile(
        "House · eletrônica local · Valencia",
        "Representante local num palco de descoberta, com um set compacto de house pensado para manter a progressão depois da abertura longa."
    ),
    "Nico Guerra" to profile(
        "Club house · groove · cena local",
        "DJ da cena local que ocupa a virada para a meia-noite com house de groove direto e função de acelerar o pequeno clube."
    ),
    "Ruben Vibes" to profile(
        "House · open format eletrônico · set longo",
        "O próprio nome entrega a proposta: duas horas de seleção acessível e dançante, transitando pelo house sem ficar preso a um único subgênero."
    ),
    "Àlex Mapi" to profile(
        "House/tech · club local · fechamento",
        "Responsável pelas duas últimas horas da sexta no Poliakow, leva a curadoria local para um fechamento mais firme e noturno."
    ),
    "Lynne" to profile(
        "EDM · warm-up · nova geração",
        "Nome emergente escolhido para abrir o Apsaras no domingo; é o set de aquecimento para o arco que sai do house e chega ao hard techno."
    ),
    "Alvama Ice" to profile(
        "Urban mashups · reggaeton · open format",
        "DJ e criador espanhol conhecido por mashups rápidos de hip-hop, trap e reggaeton; prepara o mainstage com músicas reconhecíveis antes de Tiësto."
    ),
    "Tiësto" to profile(
        "Trance · legado 2000s · retorno em 2026",
        "Além dos clássicos 'Flight 643', 'Lethal Industry' e 'Adagio for Strings', Tiësto retomou oficialmente o trance com 'Bring Me To Life' e um novo ciclo de álbum em 2026.",
        TranceFocus.TRANCE
    ),
    "T.B.A" to profile(
        "Atração a anunciar · opening ceremony",
        "Horário reservado para a cerimônia de abertura do domingo. O artista ainda não foi anunciado; o app mantém o bloco sem inventar uma identidade."
    ),
    "Oliver Heldens" to profile(
        "Future house · HI-LO · techno",
        "Pioneiro do future house e também autor do projeto techno HI-LO; Heldens já descreveu o trance como ponte entre as duas identidades, então o set pode cruzar as três fases.",
        TranceFocus.ADJACENT
    ),
    "Timmy Trumpet" to profile(
        "EDM · hardstyle · trompete ao vivo",
        "Showman australiano de 'Freaks', mistura trompete ao vivo, big room e hardstyle; costuma inserir psytrance em sets ecléticos, mas não é um artista de psytrance puro.",
        TranceFocus.ADJACENT
    ),
    "Holy Priest" to profile(
        "Hard techno · rawcore · artista mascarado",
        "Projeto mascarado alemão de som brutal e teatral; 'No Balance', 'Vielleicht Vielleicht' e a marca FXCK traduzem seus kicks secos e drops de rawcore."
    ),
    "NERVO" to profile(
        "EDM · progressive house · compositoras",
        "As gêmeas australianas Mim e Liv equilibram hits próprios e big room; antes dos palcos, coescreveram o Grammy 'When Love Takes Over', de David Guetta e Kelly Rowland."
    ),
    "Xune" to profile(
        "Tech house · Latin groove · hip-hop samples",
        "Produtor espanhol de tech house groovado, conhecido pela série #XuneTunes e por inserir percussão latina e samples de hip-hop em sets longos."
    ),
    "Lola Bozzano" to profile(
        "House · tech house · groove espanhol",
        "DJ da nova cena espanhola posicionada no aquecimento do Resonance: três horas de house/tech house antes da escalada para os headliners."
    ),
    "Hugel" to profile(
        "Latin house · Afro house · hits globais",
        "Francês que levou o Latin/Afro house ao pop global com 'I Adore You', 'Patadas de Ahogado' e o remix de 'Bella Ciao'; groove vocal e solar antes da madrugada."
    ),
    "Franky Rizardo" to profile(
        "House · tech house · FLOW",
        "Holandês criador da comunidade FLOW e da LTF Records, conhecido por house profundo e muito dirigido à pista; seu show More To Life lotou o Ziggo Dome."
    ),
    "Marco Carola" to profile(
        "Tech house · Music On · Nápoles/Ibiza",
        "Veterano napolitano que ajudou a exportar o techno italiano e fundou a Music On; célebre por mixagem em três decks e sessões longas em Ibiza."
    ),
    "Adam Beyer" to profile(
        "Techno · Drumcode · Suécia",
        "Fundador da Drumcode em 1996 e autor de 'Your Mind', traz techno grande e preciso; 'Explorer Vol. 1' marcou seu primeiro álbum em mais de duas décadas."
    ),
    "Sara Landry" to profile(
        "Hard techno · HEKATE · ETERNALISM",
        "A 'high priestess of hard techno' constrói sets intensos com estética ritual; fundou a HEKATE e levou o álbum 'Spiritual Driveby' ao projeto audiovisual ETERNALISM."
    ),
    "Furyan" to profile(
        "Hardcore · Neophyte Records · Países Baixos",
        "Produtor holandês de hardcore conhecido por engenharia de kicks e faixas como 'Rugged', 'Bombshell' e 'Teknology'; abre a fase internacional do domingo."
    ),
    "Ophidian" to profile(
        "Industrial hardcore · IDM · composição clássica",
        "Um dos produtores mais técnicos do hardcore: combina formação musical, industrial, IDM e desenho sonoro minucioso; 'Butterfly VIP' virou peça de culto."
    ),
    "Sakyra B2B Namara" to profile(
        "Hardcore B2B · uptempo · nova geração",
        "Sakyra cruza hardcore moderno e golden era; Namara empurra para o uptempo. O B2B de 90 minutos deve alternar narrativa, kicks rápidos e confronto de estilos."
    ),
    "Tha Playah" to profile(
        "Hardcore · engenharia de estúdio · State of Anarchy",
        "Pilar do hardcore holandês e produtor de estúdio respeitado, equilibra peso e arranjos sombrios em projetos como 'Sick and Twisted' e State of Anarchy."
    ),
    "Angerfist" to profile(
        "Hardcore · gabber · Masters of Hardcore",
        "O mascarado holandês é o maior emblema global do hardcore/gabber; 'Raise Your Fist' e o álbum 'Creed of Chaos' resumem seu som marcial."
    ),
    "Mad Dog" to profile(
        "Hardcore · Dogfight · downtempo revival",
        "Italiano fundador da Dogfight, percorre do hardcore clássico ao movimento downtempo/slow hardcore que ajudou a reavivar; lançou também a faixa 'Trancedrive'."
    ),
    "Anime" to profile(
        "Italian hardcore · Dogfight · Queen of Hardcore",
        "Barbara Palermo, a Anime, é uma das principais figuras do hardcore italiano: produção limpa, vocais agressivos e faixas como 'Exterminate' e 'Detonate'."
    ),
    "N-Vitral presents Bombsquad" to profile(
        "Industrial hardcore · uptempo · live concept",
        "Bombsquad é o formato de assalto de N-Vitral: meia hora concentrada de industrial hardcore, kicks mutantes e impacto visual, sem espaço para desacelerar."
    ),
    "Lil Texas" to profile(
        "Texcore · uptempo · 200+ BPM",
        "O cowboy americano batizou seu som de Texcore e levou o hardcore acima de 200 BPM a grandes festivais; performance exagerada e 'I AM EXCITED' são a assinatura."
    ),
    "DRS" to profile(
        "Uptempo hardcore · Triple Six · Bélgica",
        "Belga à frente da Triple Six Records, fecha o Arcade com uptempo extremo; séries como 'Overdose' e o hino 2026 com Drokz indicam a pressão esperada."
    ),
    "Gil" to profile(
        "Open format · hits · warm-up",
        "Abre o domingo do Beyond, agora dedicado a hits e formatos mais populares: seleção aberta para receber a pista antes da sequência urbana."
    ),
    "Joel Toro" to profile(
        "Urban · reggaeton · open format",
        "DJ da programação urbana espanhola, trabalha reggaeton e hits comerciais num set curto de festival voltado a reconhecimento imediato."
    ),
    "Danny Mad" to profile(
        "House vocal · club · produtor madrilenho",
        "Produtor de Madrid e residente de club, mistura house vocal e faixas próprias como 'Ella No Pide Permiso' e 'Tik Tak Tak' dentro do dia mais aberto do Beyond."
    ),
    "Maggie" to profile(
        "Urban hits · pop · open format",
        "Atração da faixa urbana/comercial do Beyond, com repertório aberto entre pop, reggaeton e músicas conhecidas para manter o palco em clima de festa."
    ),
    "R.Flow" to profile(
        "Urban · commercial · Valencia",
        "Nome emergente do circuito valenciano de clubs, encaixa música urbana e hits comerciais na progressão que antecede Arnny Montana."
    ),
    "Arnny Montana" to profile(
        "Reggaeton · urban · mashups",
        "DJ espanhol com mais de duas décadas de pista, especializado em reggaeton, urban e versões comerciais; é um dos nomes mais reconhecíveis deste recorte do Beyond."
    ),
    "DJ Bacardit" to profile(
        "Urban · latin · open format",
        "Set de virada da noite construído em torno de urban, latin e hits; funciona como ponte direta entre a sequência espanhola e J.Beren."
    ),
    "J.Beren" to profile(
        "Eletrônica + urban/latin · Alicante",
        "Produtor alicantino que cruza eletrônica, urbano e ritmos latinos; faixas como 'Pues Bomba' e 'Mi Ex' apontam para um set híbrido e festivo."
    ),
    "Totote" to profile(
        "Urban DJ · reggaeton · festival espanhol",
        "DJ do circuito jovem espanhol com foco em reggaeton, urban e edits de festival; assume a primeira hora da madrugada com dinâmica de festa."
    ),
    "Mon DJ" to profile(
        "Open format · remixes · urban dance",
        "DJ e produtor ligado a remixes de urban/dance, inclusive colaborações recentes com Arnny Montana; deve privilegiar versões rápidas e reconhecíveis."
    ),
    "Carlittos" to profile(
        "Commercial dance · latin · open format",
        "Seleção de madrugada voltada a dance comercial e latin hits, mantendo o Beyond acessível antes dos dois shows mais performáticos do fechamento."
    ),
    "Space Elephants" to profile(
        "EDM · dubstep · show interativo",
        "Coletivo valenciano que junta DJ e animadores fantasiados; o foco é espetáculo participativo, mashups, EDM e dubstep, não uma sessão convencional."
    ),
    "Michael Rod" to profile(
        "EDM · big festival · open format",
        "DJ espanhol com passagem por Tomorrowland e festivais como Medusa, Arenal Sound e Dreambeach; fecha o palco com linguagem de mainstage e muitos hits."
    ),
    "Taia" to profile(
        "Makina melódica · takeover Universo Makina",
        "Abre o Universo Makina com a face mais melódica e eufórica do gênero, preparando duas horas de aceleração antes de Carnada."
    ),
    "Carnada" to profile(
        "Makina · hardcore · takeover",
        "Segundo bloco do takeover, empurra a makina para kicks mais duros e hardcore, preparando a sessão histórica de Pastis, Buenri e Sisu."
    ),
    "Pastis & Buenri & DJ Sisu" to profile(
        "Makina · hardcore · lendas Pont Aeri/X-Que",
        "Pastis & Buenri representam mais de 30 anos de X-Que e da cultura makina; DJ Sisu traz a escola Pont Aeri. São cinco horas de makina, hardcore e ecos de hard trance.",
        TranceFocus.ADJACENT
    ),
    "Rage Amoretty" to profile(
        "Makina moderna · hardcore · closing set",
        "Fecha o takeover ao amanhecer com a leitura mais veloz e atual da makina/hardcore, depois de cinco horas dedicadas às raízes do estilo."
    ),
    "Sephax" to profile(
        "Hard techno · H4R · abertura",
        "Primeiro nome do takeover H4R, encarregado de estabelecer o BPM e a estética hard techno; a grafia do cartaz é Sephax, sem confundir com o hardstyle Sephyx."
    ),
    "Lucia Gea" to profile(
        "Hard/peak-time techno · 135–155 BPM · Valencia",
        "DJ valenciana de techno raw e peak-time, com sets que sobem de 135 a 155 BPM e equilibram groove de pista com pressão industrial."
    ),
    "Winson" to profile(
        "Hard techno · vocais reconhecíveis · Países Baixos",
        "Holandês da nova onda hard techno, combina kicks pesados com vocais familiares; 'Keeping Your Head Up' virou um de seus pontos de identificação."
    ),
    "Brenda Serna" to profile(
        "Techno-trance · hard trance · Espanha",
        "Uma das pioneiras espanholas do techno-trance: bateria forte, melodias de hard trance, clímax energéticos e vocais etéreos. É o set mais diretamente trance do H4R.",
        TranceFocus.TRANCE
    ),
    "Onlynumbers" to profile(
        "Hard techno eufórico · fast percussion",
        "Produtor franco-tcheco conhecido por percussão veloz e melodias eufóricas; 'Miss the Rave' mostra a nostalgia rave/trance dentro de um set que continua sendo hard techno.",
        TranceFocus.ADJACENT
    ),
    "Dyen" to profile(
        "Neo-rave · hard techno · Rotterdam",
        "Produtor de Rotterdam e fundador da RECKLESS, mistura hard techno, rave noventista e synths inspirados em trance; chega em 2026 no ciclo do álbum 'HERE WE ARE'.",
        TranceFocus.ADJACENT
    ),
    "Nico Bondi B3B Krow B3B TBR" to profile(
        "B3B · raw/industrial hard techno · fechamento",
        "Três DJs dividem o fechamento do H4R: o formato B3B favorece mudanças rápidas e uma escalada final de hard techno raw e industrial até 06h."
    ),
    "Mario Vice" to profile(
        "House underground · warm-up longo",
        "Abre o Vertigo com duas horas e meia para formar a pista; house underground e progressão gradual antes do showcase Wololo."
    ),
    "Javi Palmero" to profile(
        "House · minimal/tech · cena espanhola",
        "Set de transição no clube Vertigo, com house e minimal/tech voltados ao groove e à proximidade da cabine."
    ),
    "Wololo Soundsystem (Cortezz + Rizzu)" to profile(
        "House/tech showcase · Cortezz + Rizzu",
        "Showcase de Cortezz e Rizzu ligado ao coletivo e mídia eletrônica Wololo Sound; três horas para explorar house e tech em formato soundsystem."
    ),
    "Rendher" to profile(
        "Tech house · hip-hop 90s · No Religion Lab",
        "Produtor de Barcelona que funde tech house, samples de hip-hop dos anos 1990 e tempero latino; criou a No Religion Lab e já passou por Hï, DC-10 e Printworks."
    ),
    "Pive" to profile(
        "Techno/tech house · club espanhol · closing",
        "Nome do circuito eletrônico espanhol escolhido para as três últimas horas do Vertigo, com liberdade para levar o groove do tech house ao techno."
    ),
    "Ink 83" to profile(
        "House · open format · club local",
        "Abre o domingo no Poliakow com seleção eletrônica acessível, funcionando como descoberta local e ponto de encontro antes da noite ganhar peso."
    ),
    "Jorge Quel" to profile(
        "House/tech · cena local · set compacto",
        "DJ da cena local em uma hora de house/tech direto, mantendo a rotação rápida que define o início do Poliakow."
    ),
    "Toni Tega" to profile(
        "Dance · urban crossover · open format",
        "Faz a ponte entre os sets eletrônicos iniciais e o bloco urbano de Sweet Suarez, com uma seleção aberta de dance e hits."
    ),
    "Sweet Suarez" to profile(
        "Reggaeton · dembow · Brazilian funk",
        "DJ open format de alta energia que mistura reggaeton, dembow, trap, Jersey club e funk brasileiro; é a mudança mais urbana do Poliakow."
    ),
    "Alvaro Varen" to profile(
        "Latin house · tech house · Afro-latin",
        "Produtor barcelonês de latin/tech house e grooves afro-latinos, autor de remix oficial para 'La Rubia', de Omar Montes; devolve o palco ao house."
    ),
    "Fercho Energy" to profile(
        "Urban + house · sax ao vivo · Valencia",
        "DJ valenciano que cruza urban e eletrônica mais dura com intervenções de sax ao vivo; também lançou tech house em parceria com Alvaro Varen."
    ),
    "Saldivar" to profile(
        "Techno · Madrid · closing set",
        "DJ espanhol de techno com passagens por Pacha, Opium e eventos como ANTS e Carl Cox Invites; fecha as duas últimas horas com a leitura mais clubber do palco."
    )
)

fun artistProfile(name: String): ArtistProfile =
    artistProfiles[name] ?: error("Perfil de artista ausente: $name")

fun tranceRadarActs(): List<Act> = lineup
    .filter { it.tranceFocus != TranceFocus.NONE }
    .sortedWith(compareBy<Act>({ it.tranceFocus != TranceFocus.TRANCE }, ::actMinutes))
