@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)

    fun getRow(row: Int): List<E>
    fun getColumn(col: Int): List<E>
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height > 0 && width > 0) {
        return MatrixImpl(height, width, e);
    }
    throw(IllegalArgumentException());
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(h: Int, w: Int, e: E) : Matrix<E> {
    override val height: Int = h
    override val width: Int = w

    private val data = mutableListOf<E>();

    init {
        for (i in 0 until width * height) {
            data.add(e);
        }
    }

    override fun get(row: Int, column: Int): E = data[row * width + column];

    override fun get(cell: Cell): E = get(cell.row, cell.column);

    override fun set(row: Int, column: Int, value: E) {
        data[row * width + column] = value;
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value);

    override fun equals(other: Any?) =
        other is MatrixImpl<*> && height == other.height && width == other.width && data == other.data;

    override fun toString(): String {
        val sb = StringBuilder();
        sb.append("[");
        for (row in 0 until height) {
            var lst = mutableListOf<String>()
            for (col in 0 until width)
                lst.add(this[row, col].toString());
            sb.append("[");
            sb.append(lst.joinToString(", "));
            sb.append("]");
        }
        sb.append("]");
        return sb.toString();
    }

    override fun getRow(row: Int): List<E> {
        var list = mutableListOf<E>();
        if (row < height) {
            for (j in 0 until width) {
                list.add(this[row, j]);
            }
        }
        return list;
    }

    override fun getColumn(col: Int): List<E> {
        var list = mutableListOf<E>();
        if (col < width) {
            for (i in 0 until height) {
                list.add(this[i, col]);
            }
        }
        return list;
    }
}

