# Survivors

## Identificação

Lauren Auth Lugoch e Renata Fonseca - Sistemas de Informação

## Proposta

O projeto consiste em um jogo de sobrevivência desenvolvido em Java utilizando a biblioteca LibGDX. O jogador deve formar um grupo com cinco sobreviventes escolhidos entre dez personagens disponíveis, cada um com habilidades específicas, como pesca, caça, construção, orientação e primeiros socorros.

Após a formação do grupo, os personagens devem realizar tarefas necessárias para a sobrevivência, como coletar água, obter alimento, coletar madeira, acender fogueiras e construir abrigos. O desempenho em cada atividade depende das habilidades dos integrantes selecionados.

O objetivo do jogo é gerenciar os recursos disponíveis e distribuir as tarefas de forma eficiente, utilizando as características de cada personagem para aumentar as chances de sucesso do grupo.

---

## Processo de Desenvolvimento

Iniciamos o desenvolvimento executando os exemplos da aula e também o "Simple Game", e entendemos melhor sobre a inicialização dos recursos, o desenho na tela, renderização das imagens, e um controle básico da posição do personagem na tela. A partir disso, criamos o projeto LibGDX, e usamos o “Simple Game” como base para a estrutura inicial.

Adicionamos uma imagem para o background do primeiro mapa e um sprite teste para o personagem. Em seguida, foi implementado o personagem principal com posição baseada nas coordenadas x e y. O objetivo inicial era apenas conseguir a movimentação livre no mapa. Para isso, implementamos o controle por teclado utilizando as teclas W, A, S, D. 


O próximo passo foi criar as classes principais para a lógica de “sobreviventes”. Inicialmente, criamos as classes Personagem, Grupo, Tarefa, Rio, Fogueira... e desenvolvemos o básico de cada uma, seus atributos e métodos. Para seguir com o objetivo do trabalho, implementamos os personagens possuindo habilidades diferentes (Pesca, Cura, etc.), o grupo sendo selecionado manualmente, e fizemos testes diretamente pelo terminal para validar o funcionamento.


Depois, começamos a desenvolver as lógicas iniciais das tarefas e coleta de recursos, que são a ideia central do jogo. Para isso, pensamos em desenvolver, inicialmente, as tarefas de coletar água e madeira. Adicionamos um segundo mapa com a parte do rio, que o personagem acessa andando para baixo no mapa 1.

Para implementar a coleta dos recursos para as tarefas, criamos a coleta dos recursos utilizando a tecla E. Ao usá-la no mapa 1, coleta madeira, e ao usá-la no mapa 2, coleta água. Depois aprimoramos para a coleta ser feita em um ponto específico dos mapas (usando interação por proximidade, calculando a distância entre as coordenadas), e sinalizamos os locais com imagens, uma árvore para a coleta da madeira, e um balde vazio para a coleta de água.

Também, adicionamos um segundo personagem e fizemos a distinção dos responsáveis por cada tarefa, restringimos a permissão de cada um para a sua tarefa designada. E incrementamos a tarefa de fazer fogo. Então, as funcionalidades ficaram: tecla E colhe as madeiras, tecla F faz o fogo (no meio do mapa, onde está o tronco), e Tab troca os personagens.

---

## Entrega parcial

Funcionalidades do jogo:

* personagem com movimentação livre
* dois mapas (floresta e rio)
* árvore com coleta de madeira
* rio com coleta de água
* tronco com criação de fogo
* sistema simples de interações por teclas E, F e Tab

---

## Próximos passos:

- Como inicialmente focamos na lógica do programa, precisamos melhorar o visual, adicionar tela de start, mostrar personagens na tela, mensagens de apoio, etc.

- Desenvolver sistema de pontos por tarefa. Por exemplo, se uma pessoa com a habilidade de pesca realiza a tarefa de pescar, ela ganha os 5 pontos (para cada um dos 5 membros do grupo). Por outro lado, se uma pessoa que não tem essa habilidade realiza a tarefa de pesca, ela não ganha os pontos suficientes para todo o grupo, o que não garante a sobrevivência do grupo.

---

Link itch.io: https://renatalauren.itch.io/gamesurvivors

## Referências

A Simple Game https://libgdx.com/wiki/start/a-simple-game

Vídeo tutorial https://www.youtube.com/watch?v=aipDYyh1Mlc

Backgrounds e assets usados no tiled: https://danieldiggle.itch.io/sunnyside

Personagens: https://farm-animal.itch.io/character-pack

Fogueira e machado: https://anokolisa.itch.io/free-pixel-art-asset-pack-topdown-tileset-rpg-16x16-sprites

Abrigo: https://thomaswastaken.itch.io/tileset
