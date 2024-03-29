# Micro serviço encurtador URL

## Fluxo Back-end
![Fluxo Back-end](https://github.com/JessiiPer/mercado-livre-microservice-url/blob/master/src/main/resources/documentacao/fluxo_processo_backend.png)

## Lógica do encurtador

Sempre que o Encurtador de URL recebe um link para encurtar, ele salva esse link em um Dicionário e retorna um URL curto para o indivíduo que está solicitando o URL.

Quando um URL encurtado é fornecido ao Encurtador de URL, o Encurtador de URL examina o Dicionário e recupera o link original.

Para simplificar, vamos supor que o encurtador de URL use o número da solicitação como chave do dicionário. Podemos usar o número da solicitação porque podemos garantir que cada número da solicitação será exclusivo (não podemos ter a solicitação número 1 acontecendo duas vezes). Em seguida, podemos reduzir os URLs convertendo o número da solicitação diretamente em um link reduzido.

No entanto, esses métodos não serão dimensionados se muitos usuários começarem a usar o encurtador de URL. Por exemplo, ao escrever este artigo, o Twitter teve uma média de 330 milhões de usuários diários. Mesmo se uma fração dos usuários enviar links em um dia, esses métodos produzirão rapidamente links mais longos que o link original. Uma maneira de combater isso é usar conversões básicas para converter os números de solicitação em uma representação mais curta.

Conversões de base é o processo de conversão de números de diferentes bases de números. Os números que estamos acostumados a ver são chamados de números da base 10. A base 10 significa simplesmente que temos 10 maneiras de representar números (0–9). Hipoteticamente, podemos converter números em bases arbitrárias. Por exemplo, se usarmos os alfabetos (azAZ), juntamente com números (0 a 9) para representar números, teremos os números da base 62 (a soma de 26 letras minúsculas + 26 letras maiúsculas + 10 dígitos). 
