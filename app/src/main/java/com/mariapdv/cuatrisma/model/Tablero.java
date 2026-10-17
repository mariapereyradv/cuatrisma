package com.mariapdv.cuatrisma.model;

public class Tablero {

    // Constantes para el tamaño del tablero (7 columnas x 6 filas)
    public static final int COLUMNAS = 7;
    public static final int FILAS = 6;

    // Matriz que representa el estado del juego
    private Ficha[][] celdas;

    // Variable para rastrear si el juego ha terminado
    private boolean juegoTerminado;

    public Tablero() {
        reiniciar();
    }

    /**
     * Inicializa o reinicia el tablero con todas las celdas vacías.
     */
    public void reiniciar() {
        celdas = new Ficha[FILAS][COLUMNAS];
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                celdas[fila][col] = Ficha.VACIA;
            }
        }
        juegoTerminado = false;
    }

    /**
     * Intenta soltar una ficha en una columna.
     * @param columna Índice de la columna (0 a 6)
     * @param ficha Ficha a colocar (ROJA o AMARILLA)
     * @return El índice de la fila donde cayó la ficha, o -1 si la columna está llena.
     */
    public int soltarFicha(int columna, Ficha ficha) {
        if (juegoTerminado) {
            return -1; // No permitir movimientos si el juego terminó
        }

        if (columna < 0 || columna >= COLUMNAS) {
            return -1; // Columna inválida
        }

        // Recorremos de abajo hacia arriba (de la fila 5 a la 0) para simular la gravedad
        for (int fila = FILAS - 1; fila >= 0; fila--) {
            if (celdas[fila][columna] == Ficha.VACIA) {
                celdas[fila][columna] = ficha;

                // Verificamos si este movimiento generó una victoria
                if (verificarVictoria(ficha)) {
                    juegoTerminado = true;
                } else if (estaLleno()) {
                    juegoTerminado = true; // Empate
                }

                return fila; // Devolvemos la fila donde aterrizó
            }
        }

        return -1; // La columna está llena
    }

    /**
     * Obtiene el estado de una celda específica.
     */
    public Ficha obtenerFicha(int fila, int columna) {
        if (fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS) {
            return celdas[fila][columna];
        }
        return Ficha.VACIA;
    }

    /**
     * Verifica si el tablero está completamente lleno (Empate).
     */
    public boolean estaLleno() {
        for (int col = 0; col < COLUMNAS; col++) {
            if (celdas[0][col] == Ficha.VACIA) {
                return false; // Si la fila superior tiene un hueco, no está lleno
            }
        }
        return true;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    /**
     * Lógica central: verifica 4 en línea en todas las direcciones.
     */
    private boolean verificarVictoria(Ficha ficha) {
        // 1. Verificar Horizontal
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS - 3; col++) {
                if (celdas[fila][col] == ficha && celdas[fila][col + 1] == ficha &&
                        celdas[fila][col + 2] == ficha && celdas[fila][col + 3] == ficha) {
                    return true;
                }
            }
        }

        // 2. Verificar Vertical
        for (int col = 0; col < COLUMNAS; col++) {
            for (int fila = 0; fila < FILAS - 3; fila++) {
                if (celdas[fila][col] == ficha && celdas[fila + 1][col] == ficha &&
                        celdas[fila + 2][col] == ficha && celdas[fila + 3][col] == ficha) {
                    return true;
                }
            }
        }

        // 3. Verificar Diagonal (de arriba-izquierda abajo-derecha)
        for (int fila = 0; fila < FILAS - 3; fila++) {
            for (int col = 0; col < COLUMNAS - 3; col++) {
                if (celdas[fila][col] == ficha && celdas[fila + 1][col + 1] == ficha &&
                        celdas[fila + 2][col + 2] == ficha && celdas[fila + 3][col + 3] == ficha) {
                    return true;
                }
            }
        }

        // 4. Verificar Diagonal (de abajo-izquierda a arriba-derecha)
        for (int fila = 3; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS - 3; col++) {
                if (celdas[fila][col] == ficha && celdas[fila - 1][col + 1] == ficha &&
                        celdas[fila - 2][col + 2] == ficha && celdas[fila - 3][col + 3] == ficha) {
                    return true;
                }
            }
        }

        return false;
    }
}