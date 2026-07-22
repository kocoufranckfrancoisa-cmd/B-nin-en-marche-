package com.example.data.repository

import com.example.data.local.FavoriteDao
import com.example.data.local.FavoriteEntity
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BeninRepository(private val favoriteDao: FavoriteDao) {

    // --- FAVORITES (ROOM PERSISTENCE) ---
    val allFavorites: Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()

    fun isFavorite(id: String): Flow<Boolean> = favoriteDao.isFavorite(id)

    suspend fun toggleFavorite(item: SearchResultItem) {
        val favoriteEntity = FavoriteEntity(
            id = item.id,
            title = item.title,
            subtitle = item.subtitle,
            category = item.categoryType.label,
            description = item.snippet
        )
        // Check if exists
        // Simple insert or delete logic
        try {
            favoriteDao.insertFavorite(favoriteEntity)
        } catch (e: Exception) {
            favoriteDao.deleteFavoriteById(item.id)
        }
    }

    suspend fun removeFavorite(id: String) {
        favoriteDao.deleteFavoriteById(id)
    }

    // --- ARCHITECTURE SIMULATION FOR FUTURE REMOTE API SYNC ---
    suspend fun syncDataFromRemoteApi(): Boolean {
        // Prêt pour connexion API future
        // Simulate remote fetching delay if invoked
        kotlinx.coroutines.delay(1000)
        return true
    }

    // --- CHIFFRES CLÉS ---
    fun getKeyFacts(): List<KeyFact> = listOf(
        KeyFact("Population", "13,7 Millions", "Habitants (2024), population jeune et dynamique"),
        KeyFact("Superficie", "114 763 km²", "S'étend sur 670 km du Nord au Sud"),
        KeyFact("Capitale Politique", "Porto-Novo", "Cité historique aux 3 noms (Hogbonou, Adjacé, Porto-Novo)"),
        KeyFact("Capitale Économique", "Cotonou", "Centre commercial et portuaire abritant le marché Dantokpa"),
        KeyFact("Départements", "12 Départements", "Alibori, Atacora, Atlantique, Borgou, Collines, Couffo, Donga, Littoral, Mono, Ouémé, Plateau, Zou"),
        KeyFact("Communes", "77 Communes", "Découpées en arrondissements et villages/quartiers"),
        KeyFact("Langue Officielle", "Français", "Langues nationales: Fon, Yoruba, Bariba, Fulfulde, Dendi, Goun, Adja..."),
        KeyFact("Devise Nationale", "Fraternité - Justice - Travail", "Sceau de l'Union républicaine béninoise"),
        KeyFact("Monnaie", "Franc CFA (XOF)", "Rattaché à la Banque Centrale des États de l'Afrique de l'Ouest"),
        KeyFact("Drapeau National", "Vert, Jaune, Rouge", "Vert (Espoir), Jaune (Prospérité), Rouge (Courage des Aïeux)")
    )

    // --- HISTOIRE & FRISE CHRONOLOGIQUE ---
    fun getHistoricalPeriods(): List<HistoricalPeriod> = listOf(
        HistoricalPeriod(
            id = "hist_1",
            era = "XVIIe - XIXe Siècle",
            periodName = "Royaume du Danxomè",
            title = "L'essor du Danxomè et les Amazones",
            description = "Fondé sur le plateau d'Abomey au XVIIe siècle par Do-Aklin, le Royaume du Danxomè (Dahomey) devient une puissance militaire incontournable sous les règnes de Houégbadja, Agaja, Tegbesu, Guézo et Glèlè. Le royaume est célèbre pour les 'Mino' (nos mères), l'unique corps d'armée composé exclusivement de femmes guerrières, surnommées les 'Amazones du Dahomey'.",
            keyDetails = listOf(
                "Capitale royale: Abomey et ses 12 palais royaux (classés au Patrimoine Mondial de l'UNESCO).",
                "Symboles royaux: Le requin pour Béhanzin, le buffle pour Guézo, le lion pour Glèlè.",
                "Les Amazones (Mino): Élite militaire réputée pour sa discipline et son intrépidité face aux envahisseurs."
            )
        ),
        HistoricalPeriod(
            id = "hist_2",
            era = "XV-XIXe Siècle",
            periodName = "Royaumes du Nord & Peuples de l'Atacora",
            title = "Royaume de Nikki & Civilisation Somba",
            description = "Le Nord du Bénin s'articule autour du puissant empire Wasangari/Bariba de Nikki, réputé pour sa cavalerie royale et sa fête traditionnelle de la Gaani. Dans les montagnes de l'Atacora, le peuple Otammari bâtit les Tata Somba, d'impressionnants châteaux forts en terre cuite à deux niveaux.",
            keyDetails = listOf(
                "Fête de la Gaani à Nikki: Grande célébration annuelle de la culture Bariba et parade équestre royale.",
                "Royaumes alliés: Kouandé, Kandi et Parakou.",
                "Tata Somba: Architecture vernaculaire défensive unique inscrite au patrimoine culturel."
            )
        ),
        HistoricalPeriod(
            id = "hist_3",
            era = "1890 - 1894",
            periodName = "Résistance & Colonisation",
            title = "Les Guerres du Dahomey & Figures de Résistance",
            description = "Face à la pénétration coloniale française, le Roi Béhanzin mène une résistance acharnée de 1890 à 1894 pour préserver la souveraineté de son royaume. Dans le Nord, d'autres héros légendaires comme Bio Guéra (Guerrier Bariba) et Kaba (Chef Somba à Natitingou) s'élèvent courageusement contre l'occupation étrangère.",
            keyDetails = listOf(
                "Béhanzin 'Le Requin': Redoutable tacticien déporté en Martinique puis en Algérie.",
                "Bio Guéra (1856-1916): Prince Wasangari héros de la résistance armée dans le Borgou.",
                "Kaba (1915): Leader de l'insurrection de l'Atacora contre les réquisitions coloniales."
            )
        ),
        HistoricalPeriod(
            id = "hist_4",
            era = "1er Août 1960",
            periodName = "Indépendance Nationale",
            title = "Proclamation de l'Indépendance du Dahomey",
            description = "Le 1er août 1960, la République du Dahomey accède à l'indépendance sous la présidence d'Hubert Maga. La jeune république traverse une période d'apprentissage politique marquée par le triptyque politique Maga-Ahomadégbé-Apothey.",
            keyDetails = listOf(
                "Proclamation solennelle le 1er août 1960 à Porto-Novo.",
                "Période dite 'Quartier Latin de l'Afrique' en raison de l'excellence intellectuelle et éducative béninoise.",
                "Changement de nom: Le Dahomey devient le Bénin le 30 novembre 1975."
            )
        ),
        HistoricalPeriod(
            id = "hist_5",
            era = "1975 - 1989",
            periodName = "République Populaire du Bénin",
            title = "L'Ère PRPB sous le Général Mathieu Kérékou",
            description = "Après le coup d'État du 26 octobre 1972, le Général Mathieu Kérékou instaure en 1975 la République Populaire du Bénin (PRPB) sous l'égide du marxisme-léninisme. L'État nationalise les entreprises stratégiques et promeut l'autosuffisance.",
            keyDetails = listOf(
                "Adoption de la devise 'La révolution, c'est la lutte au quotidien'.",
                "Grandes réformes scolaires et sensibilisation patriotique.",
                "Fin du régime marquée par une grave crise économique à la fin des années 1980."
            )
        ),
        HistoricalPeriod(
            id = "hist_6",
            era = "1990 à Nos Jours",
            periodName = "Renouveau Démocratique",
            title = "Conférence Nationale de 1990 & Démocratie Moderne",
            description = "Du 19 au 28 février 1990, le Bénin entre dans l'Histoire universelle en organisant sous la présidence de Monseigneur Isidore de Souza la 'Conférence des Forces Vives de la Nation'. Première conférence nationale d'Afrique, elle ouvre une ère d'alternance démocratique pacifique exemplaire.",
            keyDetails = listOf(
                "Pères fondateurs de la démocratie: Mgr Isidore de Souza, Nicéphore Soglo, Mathieu Kérékou.",
                "Adoption de la Constitution du 11 décembre 1990 garantissant les libertés fondamentales.",
                "Succession pacifique des présidents: Nicéphore Soglo, Mathieu Kérékou, Boni Yayi, Patrice Talon."
            )
        )
    )

    // --- DÉPARTEMENTS & GÉOGRAPHIE ---
    fun getDepartments(): List<DepartmentInfo> = listOf(
        DepartmentInfo(
            name = "Alibori",
            chefLieu = "Kandi",
            population = "1 100 000 hab.",
            areaKm2 = "26 242 km²",
            communesCount = 6,
            communesList = listOf("Banikoara", "Gogounou", "Kandi", "Karimama", "Malanville", "Segbana"),
            description = "Le plus vaste département du Bénin, situé à l'extrême Nord, frontalier du Niger, du Burkina Faso et du Nigeria.",
            geographyHighlights = "Traversé par le fleuve Niger. Abrite une partie du Parc National du W.",
            religions = ReligionInfo(15, 10, 72, 3, listOf("Grande Mosquée de Kandi", "Sites de Malanville"), listOf("Tabaski", "Maouloud"), "Fortement marqué par la culture islamique et les traditions Fulfulde et Dendi."),
            touristSpots = listOf("Malanville (marché transfrontalier)", "Parc National du W", "Bords du fleuve Niger")
        ),
        DepartmentInfo(
            name = "Atacora",
            chefLieu = "Natitingou",
            population = "900 000 hab.",
            areaKm2 = "20 499 km²",
            communesCount = 9,
            communesList = listOf("Boukoumbé", "Cobly", "Kérou", "Kouandé", "Matéri", "Natitingou", "Péhunco", "Tanguiéta", "Toucountouna"),
            description = "Département montagneux du Nord-Ouest aux paysages majestueux de collines et de cascades.",
            geographyHighlights = "Chaîne de l'Atacora (Mont Sokbaro 658m), Cascades de Kota et de Tanougou.",
            religions = ReligionInfo(45, 25, 25, 5, listOf("Grottes de Tanongou", "Sites sacrés Otammari"), listOf("Fêtes des moissons Somba", "Pâques"), "Harmonie entre religions traditionnelles (Ancêtres Somba) et spiritualités monothéistes."),
            touristSpots = listOf("Parc National de la Pendjari", "Tata Somba de Boukoumbé", "Cascades de Kota", "Musée de Natitingou")
        ),
        DepartmentInfo(
            name = "Atlantique",
            chefLieu = "Allada",
            population = "1 600 000 hab.",
            areaKm2 = "3 233 km²",
            communesCount = 8,
            communesList = listOf("Abomey-Calavi", "Allada", "Kpomassè", "Ouidah", "Sô-Ava", "Toffo", "Tori-Bossito", "Zè"),
            description = "Département côtier riche en histoire vodoun, cités lacustres et étangs d'eau douce.",
            geographyHighlights = "Lac Nokoué, Lagune de Ouidah, Plaines littorales et cocoteraies.",
            religions = ReligionInfo(40, 48, 10, 2, listOf("Temple des Pythons à Ouidah", "Basilique de Ouidah", "Forêt Sacrée de Kpassè"), listOf("Fête du Vodoun (10 Janvier)", "Pèlerinage de la Popenguine/Ouidah"), "Berceau mondial du Vodoun, coexistant avec de grands centres chrétiens."),
            touristSpots = listOf("Ganvié (Venise de l'Afrique à Sô-Ava)", "Ouidah (Porte du Non-Retour)", "Royaume d'Allada", "Plage de Kpomassè")
        ),
        DepartmentInfo(
            name = "Borgou",
            chefLieu = "Parakou",
            population = "1 400 000 hab.",
            areaKm2 = "25 856 km²",
            communesCount = 8,
            communesList = listOf("Bembèrèkè", "Kalalè", "N'Dali", "Nikki", "Parakou", "Pèrèrè", "Sinendé", "Tchaourou"),
            description = "Le cœur économique du Nord Bénin, terre de la haute chevalerie Bariba et du peuple Peul.",
            geographyHighlights = "Plateaux de la savane soudanienne, forêts classées de la Sota.",
            religions = ReligionInfo(12, 23, 63, 2, listOf("Palais Royal de Nikki", "Grande Mosquée de Parakou"), listOf("Fête de la Gaani à Nikki", "Eid al-Fitr"), "Tradition équestre Bariba fusionnée avec une forte pratique musulmane."),
            touristSpots = listOf("Palais Royal de Nikki", "Musée plein air de Parakou", "Forêt de Bembèrèkè")
        ),
        DepartmentInfo(
            name = "Collines",
            chefLieu = "Dassa-Zoumé",
            population = "850 000 hab.",
            areaKm2 = "13 931 km²",
            communesCount = 6,
            communesList = listOf("Bantè", "Dassa-Zoumé", "Glazoué", "Ouèssè", "Savalou", "Savè"),
            description = "Département du centre caractérisé par ses imposants reliefs granitiques et mamelons rocheux.",
            geographyHighlights = "Les 41 collines de Dassa, Monts de Savè (Mamelles de Savè).",
            religions = ReligionInfo(28, 52, 18, 2, listOf("Grotte Notre-Dame d'Arigbo à Dassa", "Sanctuaires Mahi de Savalou"), listOf("Pèlerinage marial de Dassa (Août)", "Fête de l'Igname à Savalou (15 Août)"), "Lieu saint de pèlerinage catholique pour toute l'Afrique de l'Ouest."),
            touristSpots = listOf("Grotte d'Arigbo de Dassa", "Palais de Savalou", "Les Mamelles de Savè", "Marché de Glazoué")
        ),
        DepartmentInfo(
            name = "Couffo",
            chefLieu = "Dogbo",
            population = "880 000 hab.",
            areaKm2 = "2 404 km²",
            communesCount = 6,
            communesList = listOf("Applahaoué", "Djakotomey", "Dogbo-Tota", "Klouékanmè", "Lalo", "Toviklin"),
            description = "Département agricole du Sud-Ouest peuplé majoritairement par la communauté Adja.",
            geographyHighlights = "Traversé par le fleuve Couffo, vallées verdoyantes et terres fertiles.",
            religions = ReligionInfo(58, 32, 8, 2, listOf("Sanctuaires Vodoun d'Applahaoué", "Forêts sacrées du Couffo"), listOf("Fêtes des récoltes Adja", "Épiphanie"), "Ancrage profond dans les spiritualités traditionnelles et le culte Fâ."),
            touristSpots = listOf("Marché d'Azovè", "Vallée du Couffo", "Sources thermales de Lalo")
        ),
        DepartmentInfo(
            name = "Donga",
            chefLieu = "Djougou",
            population = "600 000 hab.",
            areaKm2 = "11 126 km²",
            communesCount = 4,
            communesList = listOf("Bassila", "Copargo", "Djougou", "Ouaké"),
            description = "Carrefour commercial stratégique du Nord-Ouest, réputé pour son artisanat et son histoire.",
            geographyHighlights = "Pémonts de l'Atacora, denses forêts de Bassila.",
            religions = ReligionInfo(15, 18, 65, 2, listOf("Mosquées historiques de Djougou", "Site de Tanéka"), listOf("Tabaski", "Fête du feu de Djougou"), "Cité marchande islamique cosmopolite avec le village perchés des Tanéka."),
            touristSpots = listOf("Cité des Tanéka (Tanéka Koko)", "Forêt de Bassila", "Marché international de Djougou")
        ),
        DepartmentInfo(
            name = "Littoral",
            chefLieu = "Cotonou",
            population = "1 200 000 hab.",
            areaKm2 = "79 km²",
            communesCount = 1,
            communesList = listOf("Cotonou (13 arrondissements)"),
            description = "Unique département-ville du Bénin, poumon économique, financier et culturel du pays.",
            geographyHighlights = "Façade atlantique, Chenal de Cotonou reliant l'océan au lac Nokoué.",
            religions = ReligionInfo(10, 68, 20, 2, listOf("Cathédrale Notre-Dame de Cotonou", "Mosquée Zongo"), listOf("Pâques", "Noël", "Fête de la Musique"), "Métropole cosmopolite accueillant tous les cultes et grandes institutions."),
            touristSpots = listOf("Marché Dantokpa", "Place de l'Amazone", "Plage de Fidjrossè", "Fondation Zinsou", "Boulevard de la Marina")
        ),
        DepartmentInfo(
            name = "Mono",
            chefLieu = "Lokossa",
            population = "550 000 hab.",
            areaKm2 = "1 605 km²",
            communesCount = 6,
            communesList = listOf("Bopa", "Comé", "Grand-Popo", "Houéyogbé", "Lokossa", "Athiémé"),
            description = "Département balnéaire et fluvio-lacustre bordant le fleuve Mono et le Togo.",
            geographyHighlights = "Lac Ahémé, Bouche du Roy (estuaire du fleuve Mono), lagunes côtières.",
            religions = ReligionInfo(48, 42, 8, 2, listOf("Sanctuaires de la Bouche du Roy", "Églises de Lokossa"), listOf("Nonvitché à Grand-Popo (Pentecôte)", "Fête du Vodoun"), "Grandes fêtes culturelles et rassemblements côtiers."),
            touristSpots = listOf("Bouche du Roy à Grand-Popo", "Lac Ahémé (Possotomè)", "Plages de Grand-Popo", "Musée de la Villa Karo")
        ),
        DepartmentInfo(
            name = "Ouémé",
            chefLieu = "Porto-Novo",
            population = "1 250 000 hab.",
            areaKm2 = "1 281 km²",
            communesCount = 9,
            communesList = listOf("Avrankou", "Adjohoun", "Aguégués", "Akpro-Missérété", "Bonou", "Dangbo", "Porto-Novo", "Sèmè-Kpodji", "Adjarra"),
            description = "Département berceau de la capitale administrative, réputé pour son architecture afro-brésilienne.",
            geographyHighlights = "Vallée de l'Ouémé (2e vallée la plus fertile d'Afrique), Lagune de Porto-Novo.",
            religions = ReligionInfo(25, 55, 18, 2, listOf("Grande Mosquée Afro-brésilienne", "Siège mondial du Christianisme Céleste à Sèmè"), listOf("Pèlerinage de la Nativité à Sèmè", "Fête du Fâ"), "Capitale mondiale de l'Église du Christianisme Céleste et joyau afro-brésilien."),
            touristSpots = listOf("Musée Honmè", "Jardin Botanique de Porto-Novo", "Musée da Silva", "Village des Aguégués")
        ),
        DepartmentInfo(
            name = "Plateau",
            chefLieu = "Pobè",
            population = "680 000 hab.",
            areaKm2 = "3 264 km²",
            communesCount = 5,
            communesList = listOf("Ifangni", "Kétou", "Pobè", "Sakété", "Adja-Ouèrè"),
            description = "Département frontalier du Nigeria, haut lieu de la culture Yoruba et Nago.",
            geographyHighlights = "Plateaux agricoles, forêts sacrées et gisements de calcaire.",
            religions = ReligionInfo(35, 40, 23, 2, listOf("Palais Royal de Kétou", "Masques Guèlèdè"), listOf("Festival des Masques Guèlèdè", "Tabaski"), "Patrimoine immatériel UNESCO des masques Guèlèdè."),
            touristSpots = listOf("Palais Royal et Porte Magique de Kétou", "Forêt Sacrée d'Ita-Gbogbo", "Ateliers Guèlèdè")
        ),
        DepartmentInfo(
            name = "Zou",
            chefLieu = "Abomey",
            population = "980 000 hab.",
            areaKm2 = "5 243 km²",
            communesCount = 9,
            communesList = listOf("Abomey", "Agbangnizoun", "Bohicon", "Cové", "Djidja", "Ouinhi", "Za-Kpota", "Zangnanado", "Zogbodomey"),
            description = "Cœur historique du Bénin, ancienne capitale du glorieux Royaume du Danxomè.",
            geographyHighlights = "Plateau d'Abomey, dépression de la Lama, rivière Zou.",
            religions = ReligionInfo(52, 38, 8, 2, listOf("Palais Royaux d'Abomey", "Temple de Neshoué"), listOf("Coutumes Royales d'Abomey", "Toussaint"), "Conservatoire des traditions royales Fon, du temple du Fâ et des danses rituelles."),
            touristSpots = listOf("Palais Royaux d’Abomey (UNESCO)", "Village souterrain de Agongointo", "Musée Historique d'Abomey", "Marché de Bohicon")
        )
    )

    // --- CULTURE GÉNÉRALE ---
    fun getCulturalItems(): List<CulturalItem> = listOf(
        CulturalItem(
            id = "cult_1",
            title = "Roi Béhanzin (1845-1906)",
            category = "Personnalités",
            subtitle = "Le Requin d'Abomey, Héros de la Résistance",
            description = "Dernier roi indépendant du Danxomè (1889-1894). Souverain visionnaire et fier, il refuse le protectorat français en déclarant : 'Si vous voulez la guerre, je suis prêt. Je ne renoncerai jamais à la terre de mes ancêtres'.",
            extraDetails = "Ses symboles royaux sont le requin ('Gbehanzin bo aïso gbe gbô'), l'œuf et le balai. Déporté en Martinique puis en Algérie où il s'éteint en 1906."
        ),
        CulturalItem(
            id = "cult_2",
            title = "Bio Guéra (1856-1916)",
            category = "Personnalités",
            subtitle = "Prince Wasangari et Héros du Borgou",
            description = "Guerrier invincible de la communauté Bariba/Wasangari. Il mène une guerre d'usure héroïque contre l'armée coloniale dans le Borgou pour défendre la dignité de son peuple.",
            extraDetails = "Une imposante statue équestre de 10 mètres à son effigie trône à l'entrée de l'Aéroport International de Cotonou."
        ),
        CulturalItem(
            id = "cult_3",
            title = "Angélique Kidjo",
            category = "Personnalités",
            subtitle = "Diva Internationale aux 5 Grammy Awards",
            description = "Née à Ouidah, Angélique Kidjo est l'une des plus grandes figures de la musique mondiale. Ambassadrice de bonne volonté de l'UNICEF, elle fait rayonner les rythmes béninois (Zinli, Afropop) sur les plus prestigieuses scènes du monde.",
            extraDetails = "Célèbre pour ses titres iconiques 'Agolo', 'Batonga', 'Wombo Lombo' et ses réinterprétations magistrales de la musique africaine."
        ),
        CulturalItem(
            id = "cult_4",
            title = "Fête Nationale du Vodoun (10 Janvier)",
            category = "Fêtes",
            subtitle = "Célébration des Arts, Spiritualités et Traditions",
            description = "Instaurée en 1993 sous le président Nicéphore Soglo, le 10 janvier est un jour férié national consacré aux traditions vodoun et aux religions ancestrales. Des dizaines de milliers de pèlerins du Bénin, du Brésil, de Haïti et des Caraïbes se rassemblent à Ouidah.",
            extraDetails = "Le festival 'Vodun Days' transforme la plage de la Porte du Non-Retour en une scène spectaculaire de danses de couvents, de défilés d'Égungun et de cérémonies d'Orixas."
        ),
        CulturalItem(
            id = "cult_5",
            title = "Musique & Rythme Zinli",
            category = "Musiques",
            subtitle = "Cadence Royale des Jarres et des Mains",
            description = "Le Zinli est un rythme royal ancestral créé sous le règne du Roi Kingblé. Il s'exécute à l'aide d'une jarre en terre cuite percutée avec un soufflet en cuir, accompagnée de gongs (Gankogui) et de battements de mains synchronisés.",
            extraDetails = "Rendu très populaire moderne par le célèbre artiste béninois Alekpehanhou, le 'Roi du Zinli'."
        ),
        CulturalItem(
            id = "cult_6",
            title = "Masques & Danses Guèlèdè",
            category = "Danses",
            subtitle = "Patrimoine Immatériel de l'Humanité (UNESCO)",
            description = "Célébré par la communauté Yoruba-Nago, le Guèlèdè est un culte rendu aux mères spirituelles ('Iya Nla'). Les danseurs masculins portent d'impressionnants masques sculptés en bois polychrome représentant la vie quotidienne ou les animaux.",
            extraDetails = "Allie satire sociale, grâce chorégraphique et puissance rituelle d'apaisement des esprits."
        ),
        CulturalItem(
            id = "cult_7",
            title = "Igname Pilée (Agoun / Foutou)",
            category = "Gastronomie",
            subtitle = "Mets Royal du Centre et du Nord Bénin",
            description = "Considérée comme la reine de la table béninoise. Des tubercules d'igname fraîchement récoltés sont bouillis puis pilés énergiquement dans un mortier en bois jusqu'à obtenir une pâte fluide et élastique.",
            extraDetails = "Traditionnellement servie chaude avec une sauce dja (tomate mijotée), une sauce gombo ou de la viande de mouton/pintade grillée. La fête de l'Igname est célébrée chaque 15 août à Savalou."
        ),
        CulturalItem(
            id = "cult_8",
            title = "Atassi (Waakye)",
            category = "Gastronomie",
            subtitle = "Le Plat Populaire des Matins Béninois",
            description = "Un mélange savoureux de riz et de haricots rouges (ou niébé) cuits ensemble avec des tiges de sorgho qui leur donnent une teinte rougeâtre caractéristique.",
            extraDetails = "Accompagné d'une sauce dja pimentée, d'œufs bouillis, de friture de poisson ou de peau de bœuf (Kponman)."
        ),
        CulturalItem(
            id = "cult_9",
            title = "Tissages & Appliqués d'Abomey",
            category = "Artisanat",
            subtitle = "Les Toiles Historiques des Rois",
            description = "Les tentures appliquées d'Abomey sont des œuvres d'art textiles créées par les artisans de la cour royale. Elles racontent les batailles, proverbes et blasons des rois à l'aide de motifs découpés cousus sur fond de toile de coton.",
            extraDetails = "Également célèbre pour le tissu Kanvo (pagne tissé à la main) et les poteries traditionnelles de Sè."
        ),
        CulturalItem(
            id = "cult_10",
            title = "Langues Nationales : Le Fon et le Bariba",
            category = "Langues",
            subtitle = "Richesse Linguistique du Sud au Nord",
            description = "Le Bénin compte plus de 50 langues nationales. Le Fon (parlé par ~50% de la population au Sud) et le Bariba (Baatonum, parlé dans le Borgou) sont parmi les plus répandus, aux côtés du Yoruba, du Goun, du Fulfulde (Peul) et de l'Adja.",
            extraDetails = "Expriment une poésie orale orale d'une finesse remarquable, transmise par les griots et les conteurs."
        )
    )

    // --- PROVERBES BÉNINOIS ---
    fun getProverbs(): List<Proverb> = listOf(
        Proverb("prov_1", "Aya ma n'nô gblé do adɔ mɛ nɔ", "Fon", "L'eau chaude n'oublie jamais qu'elle a été froide.", "Même au sommet du succès, il faut garder l'humilité et se souvenir de ses origines."),
        Proverb("prov_2", "Egbé l'ɔ, gbetɔ wɛ nyi ayi", "Goun", "Si le lézard tombe du haut du baobab sans se blesser, s'il ne trouve personne pour le féliciter, il se félicite lui-même.", "Apprenez à reconnaître votre propre valeur et vos efforts sans attendre la validation d'autrui."),
        Proverb("prov_3", "N'yé mɔ nɔ gbɛ̀ azɔ̈ à", "Yoruba", "La main qui donne est toujours au-dessus de celle qui reçoit.", "La générosité et le partage ennoblissent l'être humain."),
        Proverb("prov_4", "Kabi kora sâa nina", "Bariba", "Ce n'est pas parce que l'oiseau vole haut dans le ciel qu'il a oublié la terre.", "Ne perdez jamais de vue vos réalités fondamentales et vos racines."),
        Proverb("prov_5", "Adan mɛ nɔ ɗo xoxɔ̀ mɛ à", "Adja", "C'est à la fin de la journée qu'on sait si le marché a été bon.", "Ne jugez pas une entreprise avant son terme accompli; la patience révèle tout.")
    )

    // --- DÉCOUVRIR LE BÉNIN (SITES TOURISTIQUES) ---
    fun getTourismPlaces(): List<TourismPlace> = listOf(
        TourismPlace(
            id = "tour_1",
            name = "Ganvié - La Venise de l'Afrique",
            category = "Ville lacustre",
            department = "Atlantique (Sô-Ava)",
            description = "Plus grande cité lacustre d'Afrique avec plus de 30 000 habitants vivant dans des maisons en bois sur piotis au milieu du lac Nokoué. Fondée au XVIIIe siècle par le peuple Tofinu pour échapper aux razzias de guerriers.",
            highlights = listOf("Marché flottant sur pirogues", "Maison du tourisme sur l'eau", "Pêche artisanale à l'Acadja"),
            addressOrAccess = "Accès par pirogue ou barque motorisée depuis l'embarcadère de Calavi (20 min de traversée).",
            hotelOrFoodTip = "Hôtel Chez M: Hébergement sur pilotis pour vivre une nuit magique sur le lac."
        ),
        TourismPlace(
            id = "tour_2",
            name = "La Porte du Non-Retour & Route des Esclaves",
            category = "Lieu Historique",
            department = "Atlantique (Ouidah)",
            description = "Monument mémorial impressionnant érige sur la plage de Ouidah, marquant le lieu ultime d'embarquement des captifs africains vers les Amériques. La Route des Esclaves retrace les 4 km reliant la Place des Enchères au rivage.",
            highlights = listOf("Arbre de l'Oubli et Arbre du Retour", "Case de Zoungbodji", "Mémorial du Souvenir"),
            addressOrAccess = "Situé au bout de la Route des Esclaves, à 3 km du centre-ville de Ouidah.",
            hotelOrFoodTip = "Restaurant L'Escale à la plage: Poisson frais grillé les pieds dans le sable."
        ),
        TourismPlace(
            id = "tour_3",
            name = "Palais Royaux d'Abomey (UNESCO)",
            category = "Palais royal & Musée",
            department = "Zou (Abomey)",
            description = "Ensemble de 12 palais étalés sur 47 hectares bâtis par les souverains successifs du Royaume du Danxomè entre 1625 et 1900. Conservatoire vivant des trônes en bois sculpté, des bas-reliefs en argile et des armes des Amazones.",
            highlights = listOf("Trône du Roi Guézo posé sur des crânes d'ennemis", "Salle des tissus appliqués", "Tombes royales"),
            addressOrAccess = "Centre de la ville historique d'Abomey (2h30 de route depuis Cotonou).",
            hotelOrFoodTip = "Hôtel Sun Beach Abomey / Auberge d'Abomey: Repas traditionnels béninois."
        ),
        TourismPlace(
            id = "tour_4",
            name = "Parc National de la Pendjari",
            category = "Réserve Naturelle",
            department = "Atacora (Tanguiéta)",
            description = "L'un des plus beaux parcs d'Afrique de l'Ouest (275 000 hectares). Abrite la plus grande population de lions d'Afrique de l'Ouest, des troupeaux d'éléphants, des hippopotames, babouins, guépards et plus de 300 espèces d'oiseaux.",
            highlights = listOf("Safari en 4x4 au lever du soleil", "Mare aux Hippopotames de Bali", "Belvédère de la Pendjari"),
            addressOrAccess = "Accessible via Natitingou et Tanguiéta (Meilleure période: Décembre à Avril).",
            hotelOrFoodTip = "Pendjari Lodge: Tentes de luxe safari en plein cœur du parc."
        ),
        TourismPlace(
            id = "tour_5",
            name = "Tata Somba de Boukoumbé",
            category = "Architecture vernaculaire",
            department = "Atacora (Boukoumbé)",
            description = "Châteaux-forts miniatures en argile à deux niveaux construits par le peuple Otammari. L'étage supérieur sert d'habitation et de grenier à céréales, tandis que le rez-de-chaussée abrite le bétail et la cuisine.",
            highlights = listOf("Nuit chez l'habitant dans un Tata", "Randonnée dans les vallées de Koussoukoingou", "Artisanat en terre cuite"),
            addressOrAccess = "À 45 km à l'Ouest de Natitingou, proche de la frontière du Togo.",
            hotelOrFoodTip = "Ecolodge de Koussoukoingou: Vue panoramique sur la chaîne de l'Atacora."
        ),
        TourismPlace(
            id = "tour_6",
            name = "Musée Honmè & Grande Mosquée de Porto-Novo",
            category = "Culture & Architecture",
            department = "Ouémé (Porto-Novo)",
            description = "Le Musée Honmè est l'ancien palais du Roi Toffa 1er (1874-1908). À proximité, la Grande Mosquée présente une architecture baroque afro-brésilienne unique au monde construite au XIXe siècle par les Agudas de retour du Brésil.",
            highlights = listOf("Cour des reines et appartements royaux", "Façade colorée de la mosquée de style Salvador de Bahia", "Jardin Botanique"),
            addressOrAccess = "Quartier Quartier Zèbou, Porto-Novo (45 min de Cotonou).",
            hotelOrFoodTip = "Hôtel Louxor Porto-Novo / Restaurant La Calebasse."
        )
    )

    // --- QUIZ INTERACTIF ---
    fun getQuizQuestions(): List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            question = "Quelle est la capitale politique officielle du Bénin ?",
            options = listOf("Cotonou", "Porto-Novo", "Abomey", "Parakou"),
            correctAnswerIndex = 1,
            explanation = "Porto-Novo est la capitale politique et administrative, tandis que Cotonou est la capitale économique.",
            category = "Géographie"
        ),
        QuizQuestion(
            id = 2,
            question = "Comment appelait-on le corps d'armée féminin du Royaume du Danxomè ?",
            options = listOf("Les Amazones (Mino)", "Les Valkyries", "Les Guerrières du Fâ", "Les Kpapanou"),
            correctAnswerIndex = 0,
            explanation = "Les 'Mino' (nos mères en Fon), surnommées les Amazones du Dahomey, formaient une unité militaire d'élite féminine.",
            category = "Histoire"
        ),
        QuizQuestion(
            id = 3,
            question = "Quelle importante cité lacustre du Bénin est surnommée 'La Venise de l'Afrique' ?",
            options = listOf("Possotomè", "Ganvié", "Agongointo", "Grand-Popo"),
            correctAnswerIndex = 1,
            explanation = "Ganvié, bâtie entièrement sur pilotis sur le lac Nokoué, compte plus de 30 000 habitants.",
            category = "Géographie"
        ),
        QuizQuestion(
            id = 4,
            question = "Quelle date est consacrée chaque année à la Fête Nationale du Vodoun au Bénin ?",
            options = listOf("1er Août", "10 Janvier", "15 Août", "1er Décembre"),
            correctAnswerIndex = 1,
            explanation = "Le 10 janvier est le jour férié national célébrant les arts, la culture et la spiritualité Vodoun.",
            category = "Culture"
        ),
        QuizQuestion(
            id = 5,
            question = "Quel souverain résistant du Danxomè avait pour symbole royal le Requin ?",
            options = listOf("Roi Guézo", "Roi Béhanzin", "Roi Agaja", "Roi Tegbesu"),
            correctAnswerIndex = 1,
            explanation = "Le Roi Béhanzin ('Le Requin qui fait trembler les eaux') a dirigé la résistance contre les troupes coloniales.",
            category = "Histoire"
        ),
        QuizQuestion(
            id = 6,
            question = "Combien de départements compte la République du Bénin ?",
            options = listOf("6", "10", "12", "77"),
            correctAnswerIndex = 2,
            explanation = "Le Bénin est divisé en 12 départements administratifs comprenant 77 communes au total.",
            category = "Géographie"
        ),
        QuizQuestion(
            id = 7,
            question = "En quelle année s'est déroulée la célèbre Conférence des Forces Vives de la Nation ?",
            options = listOf("1960", "1975", "1990", "2001"),
            correctAnswerIndex = 2,
            explanation = "Du 19 au 28 février 1990, cette conférence historique a tracé la voie du renouveau démocratique béninois.",
            category = "Histoire"
        ),
        QuizQuestion(
            id = 8,
            question = "Quel parc national au Nord-Ouest du Bénin est une réserve de biosphère majeure d'Afrique de l'Ouest ?",
            options = listOf("Parc du W", "Parc National de la Pendjari", "Parc de la Boucle du Baoulé", "Réserve de Lama"),
            correctAnswerIndex = 1,
            explanation = "Le Parc National de la Pendjari dans l'Atacora est réputé pour sa faune exceptionnelle (lions, éléphants, hippopotames).",
            category = "Géographie"
        ),
        QuizQuestion(
            id = 9,
            question = "Quel plat traditionnel béninois à base d'igname pilée est célébré chaque 15 août à Savalou ?",
            options = listOf("Atassi", "Agoun (Igname pilée)", "Amiwo", "Akassa"),
            correctAnswerIndex = 1,
            explanation = "L'Agoun (Igname pilée) est le plat vedette de la Fête de l'Igname du 15 août à Savalou dans les Collines.",
            category = "Culture"
        ),
        QuizQuestion(
            id = 10,
            question = "Quelle célèbre artiste béninoise a remporté 5 Grammy Awards internationaux ?",
            options = listOf("Zénéba", "Sessimè", "Angélique Kidjo", "Bella Bellow"),
            correctAnswerIndex = 2,
            explanation = "Angélique Kidjo, originaire de Ouidah, est une légende mondiale de la musique primée par 5 Grammy Awards.",
            category = "Culture"
        )
    )

    // --- RECHERCHE GLOBALE ---
    fun searchAll(query: String): List<SearchResultItem> {
        if (query.isBlank()) return emptyList()
        val q = query.trim().lowercase()
        val results = mutableListOf<SearchResultItem>()

        // Search in Departments
        getDepartments().forEach { dept ->
            if (dept.name.lowercase().contains(q) || dept.description.lowercase().contains(q) || dept.communesList.any { it.lowercase().contains(q) }) {
                results.add(
                    SearchResultItem(
                        id = "dept_${dept.name}",
                        title = "Département de ${dept.name}",
                        subtitle = "Chef-lieu: ${dept.chefLieu} (${dept.communesCount} communes)",
                        snippet = dept.description,
                        categoryType = CategoryType.GEOGRAPHY
                    )
                )
            }
        }

        // Search in Culture
        getCulturalItems().forEach { item ->
            if (item.title.lowercase().contains(q) || item.description.lowercase().contains(q) || item.category.lowercase().contains(q)) {
                results.add(
                    SearchResultItem(
                        id = item.id,
                        title = item.title,
                        subtitle = "${item.category} • ${item.subtitle}",
                        snippet = item.description,
                        categoryType = CategoryType.CULTURE
                    )
                )
            }
        }

        // Search in History
        getHistoricalPeriods().forEach { period ->
            if (period.title.lowercase().contains(q) || period.description.lowercase().contains(q) || period.periodName.lowercase().contains(q)) {
                results.add(
                    SearchResultItem(
                        id = period.id,
                        title = period.title,
                        subtitle = "${period.periodName} (${period.era})",
                        snippet = period.description,
                        categoryType = CategoryType.HISTORY
                    )
                )
            }
        }

        // Search in Tourism Places
        getTourismPlaces().forEach { place ->
            if (place.name.lowercase().contains(q) || place.description.lowercase().contains(q) || place.category.lowercase().contains(q)) {
                results.add(
                    SearchResultItem(
                        id = place.id,
                        title = place.name,
                        subtitle = "${place.category} • ${place.department}",
                        snippet = place.description,
                        categoryType = CategoryType.TOURISM
                    )
                )
            }
        }

        // Search in Proverbs
        getProverbs().forEach { proverb ->
            if (proverb.frenchTranslation.lowercase().contains(q) || proverb.meaning.lowercase().contains(q) || proverb.originalText.lowercase().contains(q)) {
                results.add(
                    SearchResultItem(
                        id = proverb.id,
                        title = "Proverbe ${proverb.language}: \"${proverb.originalText}\"",
                        subtitle = proverb.frenchTranslation,
                        snippet = proverb.meaning,
                        categoryType = CategoryType.PROVERB
                    )
                )
            }
        }

        return results
    }
}
