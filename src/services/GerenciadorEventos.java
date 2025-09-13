package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import model.Categoria;
import model.Evento;
import model.Usuario;
import manager.FileManager;

public class GerenciadorEventos {
    private List<Evento> eventos;
    private Usuario usuarioLogado;
    private final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public GerenciadorEventos() {
        this.eventos = FileManager.carregarEventos();
    }

    public void iniciarSistema(Scanner scanner) {
        cadastrarUsuario(scanner);
        exibirMenuPrincipal(scanner);
    }

    private void cadastrarUsuario(Scanner scanner) {
        System.out.println("\n=== CADASTRO DE USUÁRIO ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = readIntSafe(scanner);
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        usuarioLogado = new Usuario(nome, email, idade, telefone, cidade);
        System.out.println("Usuário cadastrado: " + usuarioLogado);
    }

    private void exibirMenuPrincipal(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n=== SISTEMA DE EVENTOS DA CIDADE ===");
            System.out.println("1 - Cadastrar Evento");
            System.out.println("2 - Listar Todos os Eventos");
            System.out.println("3 - Listar Eventos por Categoria");
            System.out.println("4 - Confirmar Presença em Evento");
            System.out.println("5 - Cancelar Presença em Evento");
            System.out.println("6 - Meus Eventos Confirmados");
            System.out.println("7 - Eventos Ocorrendo Agora");
            System.out.println("8 - Eventos Já Realizados");
            System.out.println("9 - Próximos Eventos");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = readIntSafe(scanner);

            switch (opcao) {
                case 1: 
                    cadastrarEvento(scanner); 
                    break;
                case 2: 
                    listarTodosEventos(); 
                    break;
                case 3: 
                    listarEventosPorCategoria(scanner); 
                    break;
                case 4: 
                    confirmarPresenca(scanner); 
                    break;
                case 5: 
                    cancelarPresenca(scanner); 
                    break;
                case 6: 
                    listarMeusEventos(); 
                    break;
                case 7: 
                    listarEventosOcorrendoAgora(); 
                    break;
                case 8: 
                    listarEventosPassados(); 
                    break;
                case 9: 
                    listarProximosEventos(); 
                    break;
                case 0: 
                    FileManager.salvarEventos(eventos);
                    System.out.println("Saindo do sistema...");
                    break;
                default: 
                    System.out.println("Opção inválida!");
            }

            if (opcao != 0 && opcao != 2 && opcao != 6 && opcao != 7 && opcao != 8 && opcao != 9) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            } else if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        } while (opcao != 0);
    }

    private void cadastrarEvento(Scanner scanner) {
        System.out.println("\n=== CADASTRO DE EVENTO ===");
        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine();
        
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        
        Categoria.listarCategorias();
        System.out.print("Selecione a categoria (número): ");
        Categoria categoria = Categoria.selecionarCategoria(readIntSafe(scanner));
        scanner.nextLine();
        
        if (categoria == null) {
            System.out.println("Categoria inválida!");
            return;
        }
        
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        
        System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
        LocalDateTime horario = lerDataHora(scanner);
        
        System.out.print("Duração em horas: ");
        int duracao = readIntSafe(scanner);
        scanner.nextLine();

        Evento novoEvento = new Evento(nome, endereco, categoria, horario, descricao, duracao);
        
        if (buscarEventoPorNome(nome) != null) {
            System.out.println("Já existe um evento com este nome!");
            return;
        }

        eventos.add(novoEvento);
        System.out.println("Evento '" + nome + "' cadastrado com sucesso!");
    }

    private Evento buscarEventoPorNome(String nome) {
        for (Evento evento : eventos) {
            if (evento.getNome().equalsIgnoreCase(nome)) {
                return evento;
            }
        }
        return null;
    }

    private void listarTodosEventos() {
        System.out.println("\n=== TODOS OS EVENTOS ===");
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }
        eventos.sort(Comparator.comparing(Evento::getHorario));
        eventos.forEach(System.out::println);
    }

    private void listarEventosPorCategoria(Scanner scanner) {
        Categoria.listarCategorias();
        System.out.print("Selecione a categoria (número): ");
        Categoria categoria = Categoria.selecionarCategoria(readIntSafe(scanner));
        scanner.nextLine();
        
        if (categoria == null) {
            System.out.println("Categoria inválida!");
            return;
        }
        
        System.out.println("\n=== EVENTOS DA CATEGORIA: " + categoria.getDescricao() + " ===");
        boolean encontrado = false;
        
        for (Evento evento : eventos) {
            if (evento.getCategoria() == categoria) {
                System.out.println(evento);
                encontrado = true;
            }
        }
        
        if (!encontrado) {
            System.out.println("Nenhum evento encontrado nesta categoria.");
        }
    }

    private void confirmarPresenca(Scanner scanner) {
        System.out.println("\n=== CONFIRMAR PRESENÇA ===");
        System.out.print("Nome do evento: ");
        String nomeEvento = scanner.nextLine();
        
        Evento evento = buscarEventoPorNome(nomeEvento);
        if (evento == null) {
            System.out.println("Evento não encontrado!");
            return;
        }
        
        if (evento.isConfirmado(usuarioLogado.getNome())) {
            System.out.println("Você já confirmou presença neste evento.");
        } else {
            evento.adicionarParticipante(usuarioLogado.getNome());
            System.out.println("Presença confirmada no evento: " + evento.getNome());
        }
    }

    private void cancelarPresenca(Scanner scanner) {
        System.out.println("\n=== CANCELAR PRESENÇA ===");
        System.out.print("Nome do evento: ");
        String nomeEvento = scanner.nextLine();
        
        Evento evento = buscarEventoPorNome(nomeEvento);
        if (evento == null) {
            System.out.println("Evento não encontrado!");
            return;
        }
        
        if (!evento.isConfirmado(usuarioLogado.getNome())) {
            System.out.println("Você não tinha confirmado presença neste evento.");
        } else {
            evento.removerParticipante(usuarioLogado.getNome());
            System.out.println("Presença cancelada no evento: " + evento.getNome());
        }
    }

    private void listarMeusEventos() {
        System.out.println("\n=== MEUS EVENTOS CONFIRMADOS ===");
        boolean encontrado = false;
        
        for (Evento evento : eventos) {
            if (evento.isConfirmado(usuarioLogado.getNome())) {
                System.out.println(evento);
                encontrado = true;
            }
        }
        
        if (!encontrado) {
            System.out.println("Você não confirmou presença em nenhum evento.");
        }
    }

    private void listarEventosOcorrendoAgora() {
        System.out.println("\n=== EVENTOS OCORRENDO AGORA ===");
        boolean encontrado = false;
        
        for (Evento evento : eventos) {
            if (evento.estaOcorrendo()) {
                System.out.println(evento);
                encontrado = true;
            }
        }
        
        if (!encontrado) {
            System.out.println("Nenhum evento ocorrendo no momento.");
        }
    }

    private void listarEventosPassados() {
        System.out.println("\n=== EVENTOS JÁ REALIZADOS ===");
        boolean encontrado = false;
        
        for (Evento evento : eventos) {
            if (evento.jaOcorreu()) {
                System.out.println(evento);
                encontrado = true;
            }
        }
        
        if (!encontrado) {
            System.out.println("Nenhum evento realizado ainda.");
        }
    }

    private void listarProximosEventos() {
        System.out.println("\n=== PRÓXIMOS EVENTOS ===");
        boolean encontrado = false;
        
        for (Evento evento : eventos) {
            if (evento.estaPorVir()) {
                System.out.println(evento);
                encontrado = true;
            }
        }
        
        if (!encontrado) {
            System.out.println("Nenhum evento programado para o futuro.");
        }
    }

    private int readIntSafe(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }
    }

    private LocalDateTime lerDataHora(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine();
                return LocalDateTime.parse(input, INPUT_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.print("Formato inválido. Use (dd/MM/yyyy HH:mm): ");
            }
        }
    }
}