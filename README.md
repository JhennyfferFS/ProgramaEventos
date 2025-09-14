## Sistema de gerenciamento de eventos

Sistema desenvolvido em Java via Eclipse IDE para cadastro e gerenciamento de eventos - Trabalho para o curso de Programação de soluções computacionais da Universidade UniRitter.

## Funcionalidades

- Cadastro de usuários 
- Cadastro de eventos com categoria já pré determinada
- Confirmação e cancelamento de participação em eventos
- Listagem de eventos ( Ocorrendo agora, passados e futuros)
- Dados salvos em arquivo "events.data"
- Interface via console

## Paaso a Passo para executar
- Possuir Java JDK 21 ou superior
- Utilizar Eclipse IDE (recomendado) ou qualquer IDE Java

### Via Eclipse 
1. File → Import → General → Existing Projects into Workspace
2. Selecione a pasta do projeto
3. Clique direito em SistemaEventos.java
4. Run As → Java Application

### Via Terminal
```bash
# Compilar o projeto
javac -d bin src/app/*.java src/model/*.java src/services/*.java src/manager/*.java

# Executar o programa
java -cp bin app.SistemaEventos
