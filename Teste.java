import java.util.Random;

public class Teste {
    public static void main(String[] args) {
        String[] cnome = new String[] { "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11" };
        int[][] ligacoes = new int[11][11];

        Random random = new Random();

        for (int i = 0; i < ligacoes.length; i++) {
            for (int j = 0; j < ligacoes.length; j++) {
                ligacoes[i][j] = random.nextInt(0,2);
            }
        }

        System.out.println("    1  2  3  4  5  6  7  8  9  10 11");
        System.out.println("    |  |  |  |  |  |  |  |  |  |  |");
        for (int i = 0; i < ligacoes.length; i++) {
            if (i<9) {
                System.out.print(i+1 + "---");
            } else {
                System.out.print(i+1 + "--");
            }
            for (int j = 0; j < ligacoes.length; j++) {
                System.out.print(ligacoes[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Indice ===============================");
        for (int i = 0; i < cnome.length; i++) {
            System.out.println(i+1 + " = "+ cnome[i]);
        }
    }
}