package A3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GerarArquivosDados {

    public static void main(String[] args) {
        // tamanhos que vamos criar
        int[] tamanhos = { 1000, 5000, 10000, 50000 };

        for (int n : tamanhos) {
            String nomeArquivo = "dados_" + n + ".txt";
            try {
                gerarArquivo(nomeArquivo, n);
                System.out.println("Arquivo gerado: " + nomeArquivo + " (" + n + " números)");
            } catch (IOException e) {
                System.err.println("Erro ao gerar " + nomeArquivo + ": " + e.getMessage());
            }
        }
    }

    private static void gerarArquivo(String nomeArquivo, int quantidade) throws IOException {
        Random random = new Random();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (int i = 0; i < quantidade; i++) {
                // gera um inteiro aleatório entre 0 e 99999
                int valor = random.nextInt(100_000);
                bw.write(Integer.toString(valor));
                bw.newLine();// cada número em uma linha
            }
        }
    }
}
