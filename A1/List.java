class List {
    Node head;
    int size = 0;

    public List() {
        this.head = null;
        this.size = 0;
    }

    public void add(Vertice dado) {
        Node no = new Node(dado);
        if (head == null) {
            head = no;
        } else {
            Node current = head;
            while (current.seta != null) {
                current = current.seta;
            }
            current.seta = no;
        }
        size++;
    }

    public Vertice removeInitial() {
        if (head == null) {
            return null;
        }
        Vertice dado = head.dado;
        head = head.seta;
        size--;
        return dado;
    }

    public Vertice get(int posicao) {
        if (posicao < 0 || posicao >= size) {
            return null; // Posição inválida
        }
        Node current = head;
        for (int i = 0; i < posicao; i++) {
            current = current.seta;
        }
        return current.dado;
    }

    public Vertice remove(int posicao) {
        if (posicao < 0 || posicao >= size) {
            return null; // Posição inválida
        }

        if (posicao == 0) {
            return removeInitial();
        }

        Node current = head;
        for (int i = 0; i < posicao - 1; i++) {
            current = current.seta;
        }
        Vertice dado = current.seta.dado;
        current.seta = current.seta.seta;
        size--;
        return dado;
    }

    public List search(String creche) {
        // Conta quantos vértices têm C1 ou C2 igual ao nome indicado
        int count = 0;
        Node current = head;
        while (current != null) {
            if (creche.equals(current.dado.C1) || creche.equals(current.dado.C2)) {
                count++;
            }
            current = current.seta;
        }

        // Cria um array com os vértices encontrados
        Vertice[] encontrados = new Vertice[count];
        int idx = 0;
        current = head;
        while (current != null) {
            if (creche.equals(current.dado.C1) || creche.equals(current.dado.C2)) {
                encontrados[idx++] = current.dado;
            }
            current = current.seta;
        }

        // Ordena o array pelo campo distance (crescente)
        for (int i = 0; i < encontrados.length - 1; i++) {
            for (int j = 0; j < encontrados.length - i - 1; j++) {
                if (encontrados[j].distance > encontrados[j + 1].distance) {
                    Vertice temp = encontrados[j];
                    encontrados[j] = encontrados[j + 1];
                    encontrados[j + 1] = temp;
                }
            }
        }

        // Cria uma nova List e adiciona os vértices ordenados
        List resultado = new List();
        for (int i = 0; i < encontrados.length; i++) {
            resultado.add(encontrados[i]);
        }
        return resultado;
    }

    public int count(String creche) {
        int contador = 0;
        Node current = head;
        
        while (current != null) {
            // Verifica se a creche está em C1 ou C2
            if (creche.equals(current.dado.C1) || creche.equals(current.dado.C2)) {
                contador++;
            }
            current = current.seta;
        }
        
        return contador;
    }
}