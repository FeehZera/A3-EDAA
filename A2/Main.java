package A2;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Quick sort
        // Bubble sort
        // Shell sort
        // final int[] array = { 8, 44, 7, 10, 8, 40, 100, 0, 1, 20 };

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> array = new ArrayList<>();

        while (running) {
            menu();
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    show(array);
                    break;
                case 2:
                    array = charge(array, scanner);
                    break;
                case 3:
                    bubbleSort(array);
                    break;
                case 4:
                    quickSort(array);
                    break;
                case 5:
                    shellSort(array);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Opcao invalida");
                    break;
            }
        }

    }

    private static void bubbleSort(ArrayList<Integer> vetor) {
        BenchMark bench = new BenchMark();
        bench.start();
        int n = vetor.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (vetor.get(j) > vetor.get(j + 1)) {
                    // Troca (Swap)
                    int temp = vetor.get(j);
                    vetor.set(j, vetor.get(j + 1));
                    vetor.set(j + 1, temp);
                }
            }
        }
        bench.stop();
        show(vetor);
        time(bench);
    }

    private static void quickSort(ArrayList<Integer> vetor) {
        quickSort(vetor, 0, vetor.size() - 1);
        show(vetor);
    }

    // Método recursivo interno - recebe início e fim
    private static void quickSort(ArrayList<Integer> vetor, int inicio, int fim) {
        if (inicio < fim) {
            // Lógica de particionamento integrada
            int pivo = vetor.get(fim); // Escolhe o último como pivô
            int i = (inicio - 1);

            for (int j = inicio; j < fim; j++) {
                if (vetor.get(j) <= pivo) {
                    i++;
                    // Troca vetor[i] com vetor[j]
                    int temp = vetor.get(i);
                    vetor.set(i, vetor.get(j));
                    vetor.set(j, temp);
                }
            }
            // Coloca o pivô no lugar certo
            int temp = vetor.get(i + 1);
            vetor.set(i + 1, vetor.get(fim));
            vetor.set(fim, temp);

            int indicePivo = i + 1;

            // Chamada Recursiva (chama a si mesmo)
            quickSort(vetor, inicio, indicePivo - 1); // Lado esquerdo
            quickSort(vetor, indicePivo + 1, fim); // Lado direito
        }
    }

    private static void shellSort(ArrayList<Integer> vetor) {
        int n = vetor.size();

        // Começa com um gap grande e divide por 2 a cada loop
        for (int gap = n / 2; gap > 0; gap /= 2) {

            // Faz um Insertion Sort gapped (com intervalo)
            for (int i = gap; i < n; i++) {
                int temp = vetor.get(i);
                int j;

                for (j = i; j >= gap && vetor.get(j - gap) > temp; j -= gap) {
                    vetor.set(j, vetor.get(j - gap));
                }
                vetor.set(j, temp);
            }
        }
        show(vetor);
    }

    private static ArrayList<Integer> charge(ArrayList<Integer> array, Scanner scanner) {
        array.clear();
        System.out.println("Informe o nome do arquivo a ser carregado:");
        String name = scanner.next();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("A2/" + name + ".txt"))) {
            String linha;
            int indice = 0;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    int valor = Integer.parseInt(linha.trim());
                    if (indice < array.size()) {
                        array.set(indice, valor);
                    } else {
                        array.add(valor);
                    }
                    indice++;
                }
            }
            System.out.println("Arquivo carregado com sucesso! Total de valores: " + array.size());
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return array;
    }
    
    private static void show(ArrayList<Integer> lista) {
        System.out.println("==========================================");
        for (int i = 0; i < lista.size(); i++) {
            System.out.print(lista.get(i) + " ");
        }
        System.out.println();
    }

    private static void time(BenchMark bench) {
        System.out.println("==========================================");
        System.out.println("Tempo de execucao: " + bench.getTime());
    }

    private static void menu() {
        System.out.println("= Menu ===================================");
        System.out.println("1. Mostrar array inicial");
        System.out.println("2. Preencher array inicial com arquivos");
        System.out.println("3. Ordenar por Bubble Sort");
        System.out.println("4. Ordenar por Quick Sort");
        System.out.println("5. Ordenar por Shell Sort");
        System.out.println("0. Sair");
        System.out.println("==========================================");
    }
}
