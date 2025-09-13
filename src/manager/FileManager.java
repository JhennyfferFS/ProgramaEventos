package manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Evento;

public class FileManager {
    private static final String DATA_FILE = "events.data";
    private static final String SEPARATOR = "|";

    public static void salvarEventos(List<Evento> eventos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(eventos);
            System.out.println("Eventos salvos com sucesso em " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Evento> carregarEventos() {
        File arquivo = new File(DATA_FILE);
        if (!arquivo.exists()) {
            System.out.println("Arquivo de eventos não encontrado. Criando novo...");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            List<Evento> eventos = (List<Evento>) ois.readObject();
            System.out.println(eventos.size() + " eventos carregados com sucesso!");
            return eventos;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar eventos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}