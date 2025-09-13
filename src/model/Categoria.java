package model;

public enum Categoria {
    FESTA("Festa"),
    ESPORTE("Evento Esportivo"),
    SHOW("Show"),
    CONGRESSO("Congresso"),
    PALESTRA("Palestra"),
    GASTRONOMICO("Evento Gastronômico"),
    CULTURAL("Evento Cultural");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static void listarCategorias() {
        System.out.println("\nCategorias disponíveis:");
        for (int i = 0; i < values().length; i++) {
            System.out.println((i + 1) + " - " + values()[i].getDescricao());
        }
    }

    public static Categoria selecionarCategoria(int opcao) {
        if (opcao >= 1 && opcao <= values().length) {
            return values()[opcao - 1];
        }
        return null;
    }
}