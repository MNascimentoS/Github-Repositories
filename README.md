# Github Repositories

![LINE](https://img.shields.io/badge/line--coverage-86%25-brightgreen.svg)
![COMPLEXITY](https://img.shields.io/badge/complexity-2,34-brightgreen.svg)

O Projeto Github Repositories tem como objetivo consultar a API aberta do Github, carregando os repositórios com mais estrelas e permitindo que o usuário visualize, além de exibir detalhes os detalhes do projeto e permitir que o usuário acesse o mesmo.

## Download
O app está disponível para download no *AppCenter* pelo seguinte link:

[Link para Download](https://install.appcenter.ms/orgs/Mateus-Nascimento/apps/Git-Repository)

## Tecnologias Utilizadas

### AppCenter (Analytics, Crashes, Distribute)

Adicionei a integraçao do AppCenter a fim de facilitar a entrega dos apks para os testadores. O AppCenter tem uma API aberta que permite a automação no deploy de criação das aplicações, além de alertar os usuários sobre novas atualizações, in-app-update, analytics e crashes.

### Fastlane

Integrei ao projeto o Fastlane que pe uma lib criada para facilitar a automatização do processo de integração e delivery do projeto. Adicionei algumas actions próprias e alguns modelos de teste, reports, deploy para o AppCenter e Google Play.

*Comandos utilizados no projeto:*

- `fastlane test` - executar os testes
- `fastlane coverage_report` - gera um coverage report
- `fastlane appcenter_build` - build de apk e upload para o appcenter

*Outros comandos disponíveis:*

- `fastlane bundle_release` - build de aab
- `fastlane build` - build de apk
- `fastlane appcenter` - upload para o appcenter
- `fastlane google_play` - deploy de aab para a google play 
- `fastlane google_play_apk` - deploy de apk para a google play 
- `fastlane post_to_slack`- post a message t slack

### Github Actions

A fim de automatizar o processo de deploy, configurei o continuos delivery do Github no projeto. Toda vez que um deploy é feito na master, é rodada uma pipeline que executa as actions do Fastlane e faz o deploy no AppCenter, distribuindo também para os testadores.

### Jetpack Navigation

Adicionei o Navigation para facilitar a navegação entre os fragments. Ele permite que passemos argumentos entre os fragments e deixa todo o processo de inflar o mesmo na tela com o sistema.

### Jetpack Paging Library

Utilizei a biblioteca de paginação do Android para carregar os repositórios da API do Github. Nela, basta criar um dataSource indicando de onde a informação será carregada (seja de uma API externa, ou um banco de dados), e o mesmo faz todo o processo de carregamento e paginação das informações.

### MVVM

Utilizei o padrão de projeto MVVM com o intuito de ter como ganho o processo de ciclo de vida do ViewModel integrado a Activity. Isso permite que os fragments tenham acesso ao ViewModel, além de facilitar o processo de criação/destruição da activity, mantendo os dados armazenados, diminuindo, por exemplo, as chances de perda de informações na hora da rotação da tela.

Junto a ele, utilizei o LiveData e MultebleLiveData que atrelados ao LiveCyclerOwner, informa todos os componentes que estão o observando quando ocorre alguma mudança.

### Koiin

Para injeção de dependência, utilizei a biblioteca Koin com o objetivo de delegar a responsabilidade de inicialização das dependências. 

### Testes

Fiz alguns testes na plataforma utilizando o Espresso para testes de ui, além de criar alguns testes unitários. Ao final, decidi integrar com a biblioteca Jacoco, para que seja possível criar relatórios e cobertura de teste.