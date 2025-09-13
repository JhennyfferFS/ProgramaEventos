package app;

import java.util.Scanner;
import services.GerenciadorEventos;

public class SistemaEventos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorEventos gerenciador = new GerenciadorEventos();
        
        System.out.println("BEM-VINDO AO SISTEMA DE EVENTOS DA CIDADE!");
        gerenciador.iniciarSistema(scanner);
        
        scanner.close();
    }
}