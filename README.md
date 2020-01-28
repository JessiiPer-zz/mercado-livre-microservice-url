# Micro serviço encurtador URL



Sempre que o Encurtador de URL recebe um link para encurtar, ele salva esse link em um Dicionário e retorna um URL curto para o indivíduo que está solicitando o URL.

Quando um URL encurtado é fornecido ao Encurtador de URL, o Encurtador de URL examina o Dicionário e recupera o link original.

Para simplificar, vamos supor que o encurtador de URL use o número da solicitação como chave do dicionário. Podemos usar o número da solicitação porque podemos garantir que cada número da solicitação será exclusivo (não podemos ter a solicitação número 1 acontecendo duas vezes). Em seguida, podemos reduzir os URLs convertendo o número da solicitação diretamente em um link reduzido:

