package sk.portugal.leksi.oldimp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.oldimp.service.OldImportService;

import java.util.Arrays;

public class OldImport {

    private static String[] words = {
            "abacaxi",
            "abertura",
            "abismo",
            "aborrecido",
            "abre-garrafas",
            "abre-latas",
            "abreviar",
            "abreviatura",
            "absolutismo",
            "absolutista",
            "absurdo",
            "abuso",
            "acampamento",
            "aceitação",
            "aconselhável",
            "acontecimento",
            "activar",
            "actividade",
            "activo",
            "acto",
            "actriz",
            "actual",
            "actualidade",
            "acusação",
            "acusado",
            "açucarar",
            "adequado",
            "adesão",
            "adicional",
            "adjectivo",
            "administrador",
            "administrativo",
            "admissão",
            "ado(p)tivo",
            "ado(p)ção",
            "advérbio",
            "agente",
            "agência",
            "agitação",
            "agradável",
            "agradecimento",
            "agrário",
            "agressivo",
            "agressor",
            "agricultura",
            "agricultor",
            "agronomia",
            "agrónomo",
            "ajudante",
            "alargamento",
            "alarme",
            "Albânia",
            "alcunha",
            "alegre",
            "alegria",
            "Alemanha",
            "alérgico",
            "alfabético",
            "alfarrabista",
            "alheio",
            "alimentação",
            "alma",
            "alternativa",
            "altura",
            "amabilidade",
            "amável",
            "ambiental",
            "ambiente",
            "ameaça",
            "América",
            "amigável",
            "amplo",
            "eco",
            "ecoar",
            "ecológico",
            "economista",
            "economizar",
            "económico",
            "ecrã",
            "edição",
            "educação",
            "educado",
            "efectivo",
            "efectuar",
            "efeito",
            "eficaz",
            "eficácia",
            "eixo",
            "electricidade",
            "electricista",
            "elegante",
            "elegância",
            "eleição",
            "eleitoral",
            "elemento",
            "eléctrico",
            "embaixadora",
            "embaixatriz",
            "embalagem",
            "ementa",
            "emergência",
            "emigração",
            "emoção",
            "empregado",
            "empregador",
            "empresa",
            "empresário",
            "encomenda",
            "energético",
            "energia",
            "enérgico",
            "enfermeira",
            "engano",
            "enriquecer",
            "enriquecimento",
            "entendimento",
            "entidade",
            "entrega",
            "entrevista",
            "envelhecimento",
            "episódio",
            "equilíbrio",
            "equipamento",
            "equipar",
            "erro",
            "Escandinávia",
            "escolar",
            "escolha",
            "esconderijo",
            "escova",
            "Escócia",
            "escuridão",
            "esfera",
            "eslavo",
            "Espanha",
            "especial",
            "especialização",
            "específico",
            "esperança",
            "esposa",
            "espuma",
            "esquema",
            "esquerdo",
            "estabelecimento",
            "rabino",
            "racional",
            "racismo",
            "racista",
            "raça",
            "radical",
            "radicalismo",
            "rapidez",
            "raro",
            "rasgar",
            "razão",
            "razoável",
            "reação",
            "real1",
            "recado",
            "recapitulação",
            "recapitular",
            "recente",
            "recentemente",
            "recibo",
            "recipiente",
            "redução",
            "reflexivo",
            "reflexo",
            "reforma",
            "reformar",
            "regional",
            "regra",
            "regulação",
            "regular1",
            "reitor",
            "relativo",
            "relatório",
            "relâmpago",
            "religioso",
            "relógio",
            "república",
            "reserva",
            "responsabilidade",
            "responsável",
            "restante",
            "restaurante",
            "resto",
            "revisão",
            "revolução",
            "riqueza",
            "riso",
            "nacional",
            "nacionalidade",
            "nacionalista",
            "natural",
            "natureza",
            "navegador",
            "necessário",
            "negativo",
            "negociação",
            "negócio",
            "nervoso",
            "neutral",
            "neve",
            "normal1",
            "Noruega",
            "norueguês",
            "noturno",
            "novidade",
            "numeral",
            "nuvem",
            "sabedoria",
            "sabor",
            "saboroso",
            "saco",
            "sagrado",
            "salgar",
            "sanção",
            "santo",
            "satisfação",
            "satisfeito",
            "saudade",
            "sector",
            "seguinte",
            "segundo1",
            "seguro",
            "seleccionar",
            "senado",
            "senador",
            "senhora",
            "sentido",
            "separado",
            "sesta",
            "sigla",
            "significado",
            "signo",
            "silêncio",
            "sinceramente",
            "sinceridade",
            "sincero",
            "sistema",
            "situação",
            "social",
            "socialismo",
            "socialista",
            "sociedade",
            "sociólogo",
            "soldado",
            "solução",
            "sombra",
            "sorridente",
            "sorte",
            "sossegado",
            "sossego",
            "soutien",
            "sozinho",
            "suave",
            "substantivo",
            "substância",
            "sucesso",
            "sueco",
            "Suécia",
            "suficiente",
            "sufixo",
            "suicidar-se",
            "suicídio",
            "sujidade",
            "sujo",
            "zangado",
            "yoga",
            "xadrez",
            "whisky",
            "windsurf",
            "Ucrânia",
            "uísque",
            "ultramar",
            "ultramarino",
            "unidade",
            "unido",
            "universal",
            "universitário",
            "universo",
            "urgente",
            "uso",
            "utensílio",
            "utilização",
            "tabacaria",
            "talento",
            "tamanho",
            "tampa",
            "tarefa",
            "tarifa",
            "taxa",
            "teimoso",
            "telecomunicação",
            "tema",
            "temperatura",
            "tempestade",
            "temporário",
            "tenda",
            "tendência",
            "teoria",
            "terramoto",
            "terreno",
            "terrível",
            "técnica",
            "técnico",
            "toilete",
            "torto",
            "trabalhador",
            "tradicional",
            "tranquilo",
            "tropical",
            "turismo",
            "qualidade",
            "quantia",
            "quantidade",
            "querido",
            "quieto",
            "quilograma",
            "quiosque",
            "quotidiano",
            "objectivo",
            "objecto",
            "obrigatório",
            "ocasião",
            "ocidental",
            "ocupação",
            "oferta",
            "omissão",
            "omitir",
            "onda",
            "oportunidade",
            "optimismo",
            "optimista",
            "organismo",
            "organização",
            "origem",
            "ouro",
            "ozono",
            "faca",
            "face",
            "facto",
            "factor",
            "factura",
            "falso",
            "fama",
            "família",
            "famoso",
            "fábrica",
            "fechado",
            "federal",
            "feliz",
            "ferida",
            "festival",
            "ficheiro",
            "filha",
            "fim",
            "financeiro",
            "financiar",
            "firma",
            "fogo",
            "formulário",
            "fornecedor",
            "fornecimento",
            "fósforo",
            "França",
            "frágil",
            "frito",
            "fumador",
            "fumo",
            "funcionário",
            "função",
            "fundamental",
            "fundar",
            "futuro",
            "fuzilar",
            "gabinete",
            "gado",
            "galeria",
            "gás",
            "gelo",
            "generoso",
            "gente",
            "gentil",
            "gerente",
            "gênero",
            "giro",
            "global",
            "globo",
            "gosto",
            "governador",
            "governo",
            "gratuito",
            "grau",
            "grátis",
            "greve",
            "Grécia",
            "grito",
            "grupo",
            "gruta",
            "guarda-chuva",
            "queixa",
            "harmonia",
            "hábito",
            "hemisfério",
            "hesitação",
            "hipermercado",
            "histórico",
            "Holanda",
            "honesto",
            "honor",
            "honra",
            "horário",
            "horizonte",
            "horrível",
            "horror",
            "hospitalidade",
            "hotel",
            "humidade",
            "humildade",
            "humilde",
            "Hungria",
            "labirinto",
            "lado",
            "ladra",
            "lata",
            "lateral",
            "latim",
            "latino",
            "legal",
            "legenda",
            "lei",
            "leitura",
            "lembrança",
            "lente",
            "leve",
            "léxico",
            "liberal",
            "liberdade",
            "liderança",
            "liderar",
            "ligado",
            "ligeiro",
            "limitado",
            "limpeza",
            "limpo",
            "lindo",
            "lista",
            "literal",
            "literário",
            "Lituânia",
            "livraria",
            "lixo",
            "loja",
            "longo",
            "louco",
            "louça",
            "lua",
            "lucro",
            "lugar",
            "lume",
            "luz",
            "jacaré",
            "jaguar",
            "Japão",
            "jarra",
            "jejum",
            "jornal",
            "junto",
            "jurista",
            "justiça",
            "juventude",
            "júri",
            "ibérico",
            "ideal",
            "ideia",
            "identidade",
            "igual",
            "igualdade",
            "ilegal",
            "ilusão",
            "imaginação",
            "imigração",
            "imperativo",
            "importador",
            "importante",
            "importância",
            "impossível",
            "impressão",
            "improvável",
            "incorrecto",
            "independente",
            "individual",
            "indivíduo",
            "industrial",
            "indústria",
            "inesperado",
            "inferno",
            "infinito",
            "infinitivo",
            "influência",
            "informal",
            "Inglaterra",
            "injustiça",
            "inocente",
            "inscrever",
            "inseguro",
            "instituição",
            "instituto",
            "inteiro",
            "inteligente",
            "inteligência",
            "intenso",
            "interesse",
            "internacional",
            "internet",
            "investimento",
            "machado",
            "madrugada",
            "maioria",
            "maldade",
            "maneira",
            "mapa",
            "maravilhoso",
            "maturidade",
            "máximo",
            "mecanismo",
            "mediterrânico",
            "meia",
            "meio",
            "menina",
            "mente",
            "menu",
            "mercado",
            "mestre",
            "metade",
            "metáfora",
            "metálico",
            "médium",
            "microondas",
            "milagre",
            "miligrama",
            "mililitro",
            "militar1",
            "ministério",
            "minoria",
            "mistério",
            "mistura",
            "mínimo",
            "mobília",
            "mochila",
            "moça",
            "moçambicano",
            "Moçambique",
            "modesto",
            "modo",
            "molhado",
            "momento",
            "monarquia",
            "monumento",
            "moral",
            "dado1",
            "dali",
            "danar",
            "dano 1",
            "dantes",
            "Danúbio",
            "daqui",
            "dar2",
            "datar",
            "debaixo",
            "década",
            "decibel",
            "decisão",
            "declinar",
            "decorar1",
            "dedicação",
            "dedução",
            "defeito",
            "defensa",
            "definitivo",
            "delgado",
            "deliberado",
            "delicioso",
            "demanda",
            "demandar",
            "demissão",
            "democracia",
            "dentro",
            "departamento",
            "depoimento",
            "derradeiro",
            "desafio",
            "desagradável",
            "desassossego",
            "desatento",
            "desânimo",
            "descanso",
            "desempregado",
            "desemprego",
            "desentendimento",
            "bacharelato",
            "bacon",
            "bairro",
            "balcão",
            "bancarrota",
            "banho",
            "banir",
            "bar1",
            "barbar",
            "barulho",
            "batalha",
            "beleza",
            "belo",
            "bicho",
            "bocado",
            "bofetada",
            "boleia",
            "bolso",
            "bomba",
            "bombeiro",
            "boneca",
            "cabeleireiro",
            "cabina",
            "Cabo Verde",
            "cabra 1",
            "cachimbo",
            "cadeia",
            "caipirinha",
            "cais",
            "calmo",
            "calor",
            "cambiar",
            "caminho",
            "camioneta",
            "cansado",
            "canto1",
            "caos",
            "capacidade",
            "capaz",
            "capital1",
            "caraterizar",
            "carga",
            "carimbo",
            "casal",
            "castelo",
            "catedral",
            "categoria",
            "cauteloso",
            "cd",
            "cedilha",
            "censura",
            "célebre",
            "chave",
            "chegada",
            "cheiro",
            "cheque",
            "chiclete",
            "choro",
            "churrasco",
            "chuva",
            "chuvisco",
            "cigarro",
            "cinta",
            "civilização",
            "classe",
            "cliente",
            "clima",
            "pacato",
            "pacote",
            "pagamento",
            "paisagem",
            "palavra-chave",
            "palestra",
            "pancada1",
            "parabéns",
            "parado",
            "parecer1",
            "parir",
            "parlamento",
            "parque",
            "parte",
            "partido",
            "partilhar",
            "passado",
            "passatempo",
            "passo",
            "pastel1",
            "patinhar",
            "paz",
            "página",
            "pálido",
            "pecado",
            "pena1",
            "pensamento",
            "pensar1",
            "pensão",
            "pente",
            "perca1",
            "perfeito",
            "perfil",
            "perfume",
            "perigo",
            "perigoso",
            "perito",
            "personagem",
            "peruca",
            "pesadelo",
            "pesca",
            "pesquisa",
            "pessoal",
            "pilha1",
            "pinga",
            "pistola",
            "planeta",
            "poesia",
            "poderoso",
            "pobreza"

    };


    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        OldImportService importService = (OldImportService) ctx.getBean("importService");

        importService.generateImport(Arrays.asList(words));
    }
}
